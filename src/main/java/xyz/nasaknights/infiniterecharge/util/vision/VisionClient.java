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

    /**
     * The boolean that will change based on the whether or not
     * the command is running and tells the Raspberry Pi to start
     * the PID.
     */
    private volatile boolean buttonPressed = false;

    private double angle, throttle, turn, distance;

    private long receivedDataCount = 0;

    private Spark light;

    public VisionClient(String ipAddr, int port) throws VisionClientInitializationException
    {
        try
        {
            visionSocket = new Socket(ipAddr, port);
        } catch (Exception e)
        {
            throw new VisionClientInitializationException("Socket");
        }

        try
        {
            serverReader = new BufferedReader(new InputStreamReader(visionSocket.getInputStream()));
        } catch (Exception e)
        {
            throw new VisionClientInitializationException("Input Reader");
        }

        try
        {
            serverWriter = new DataOutputStream(visionSocket.getOutputStream());
        } catch (Exception e)
        {
            throw new VisionClientInitializationException("Output Stream");
        }

        light = new Spark(Constants.VISION_CLIENT_LIGHT_SOURCE);
    }

    public void run()
    {
        try
        {
            serverWriter.writeByte((buttonPressed ? 1 : 0));
            String data = serverReader.readLine();
            if (data != null)
            {
                receivedDataCount++;
            }
            parseData(data);
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void parseData(String unparsed)
    {
        this.data = unparsed;
        //        String data = unparsed;
        //        double[] values = new double[NUMBER_OF_VALUES];

        // loop that encompasses the expected number of
        //        for (int i = 0; i < NUMBER_OF_VALUES; i++)
        //        {
        //            if (data.contains(","))
        //            {
        //                values[i] = Double.parseDouble(data.substring(0, data.indexOf(",") - 1).trim());
        //                data = data.substring(data.indexOf(",") + 1);
        //            } else
        //            {
        //                values[i] = Double.parseDouble(data);
        //            }
        //        }

        String[] stringArray = unparsed.split(",");
        double[] dataArray = new double[NUMBER_OF_VALUES];
        for (int i = 0; i < NUMBER_OF_VALUES; i++)
        {
            dataArray[i] = Double.parseDouble(stringArray[i].trim());
        }

        this.dataArray = dataArray;
        // these values are very subject to change
        // x (0), z (1), dist (2), angle (3), angle2 (4),
        // OffSetx (5), speed (6), turn (7), gyro (8)
        distance = this.dataArray[2];
        angle = this.dataArray[4];
        throttle = this.dataArray[6];
        turn = this.dataArray[7];
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
        light.set(on ? 1 : 0);
    }

    public double getDistance()
    {
        return distance;
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

    public long getReceivedDataCount()
    {
        return receivedDataCount;
    }

    /**
     * This class has been implemented for better testing and calibration purposes only and
     * should be removed once vision client is fully operational due to its over-complex nature.
     */
    public static class VisionClientInitializationException extends Exception
    {
        public VisionClientInitializationException(String location)
        {
            super("Failed to initialize the Vision Client.\nLocation: " + location);
        }
    }
}