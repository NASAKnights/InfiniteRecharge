package xyz.nasaknights.infiniterecharge.util.vision;

import edu.wpi.first.wpilibj.Spark;
import xyz.nasaknights.infiniterecharge.Constants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class VisionClient extends Thread
{
    private Socket visionSocket;
    private BufferedReader serverReader;
    private DataOutputStream serverWriter;
    private byte NUMBER_OF_VALUES = 9;
    private double[] dataArray;

    private String data;

    private boolean buttonPressed = false;

    // update as these are subject to change, TODO Get finalized data format before final commit
    private double angle, throttle, turn;

    private Spark light;

    public VisionClient(String ipAddr, int port) throws VisionClientInitializationException
    {
        try
        {
            visionSocket = new Socket(ipAddr, port);
        } catch (Exception e)
        {
            throw new VisionClientInitializationException("Location: Socket");
        }

        try
        {
            serverReader = new BufferedReader(new InputStreamReader(visionSocket.getInputStream()));
        } catch (Exception e)
        {
            throw new VisionClientInitializationException("Location: Input Reader");
        }

        try
        {
            serverWriter = new DataOutputStream(visionSocket.getOutputStream());
        } catch (Exception e)
        {
            throw new VisionClientInitializationException("Location: Output stream.");
        }

        light = new Spark(Constants.VISION_CLIENT_LIGHT_SOURCE);
    }

    public void run()
    {
        try
        {
            // verify what needs to be written to the vision server
            serverWriter.writeChars(((buttonPressed) ? 1 : 0) + "\n");
            String data = serverReader.readLine();
            parseData(data);
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void parseData(String unparsed)
    {
        this.data = unparsed;
        String data = unparsed;
        double[] values = new double[NUMBER_OF_VALUES];

        // loop that encompasses the expected number of
        for (int i = 0; i < NUMBER_OF_VALUES; i++)
        {
            if (data.contains(","))
            {
                values[i] = Double.parseDouble(data.substring(0, data.indexOf(",") - 1).trim());
                data = data.substring(data.indexOf(",") + 1);
            } else
            {
                values[i] = Double.parseDouble(data);
            }
        }
        dataArray = values;
        // these values are very subject to change
        // x (0), z (1), dist (2), angle (3), angle2 (4),
        // OffSetx (5), speed (6), turn (7), gyro (8)
        angle = values[4];
        throttle = values[6];
        turn = values[7];
    }

    public void setButtonPressed(boolean pressed)
    {
        buttonPressed = pressed;
    }

    public boolean getButtonPressed()
    {
        return buttonPressed;
    }

    public void setLightOn(boolean on)
    {
        light.set(on ? -1 : 0);
    }

    public double getAngle()
    {
        return angle;
    }

    public double getThrottle()
    {
        return throttle;
    }

    public double getTurn()
    {
        return turn;
    }

    public double[] getDataArray()
    {
        return dataArray;
    }

    public String getData()
    {
        return data;
    }

    /**
     * This class has been implemented for better testing and calibration purposes only and
     * should be removed once vision client is fully operational due to its over-complex nature.
     */
    public static class VisionClientInitializationException extends Exception
    {
        public VisionClientInitializationException(String message)
        {
            super("Failed to initialize the Vision Client.\n" + message);
        }
    }
}