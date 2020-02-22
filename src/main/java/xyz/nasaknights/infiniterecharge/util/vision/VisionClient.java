package xyz.nasaknights.infiniterecharge.util.vision;

import xyz.nasaknights.infiniterecharge.Constants;

import java.io.*;
import java.net.Socket;

public class VisionClient extends Thread
{
    private Socket visionSocket;
    private BufferedReader serverReader;
    private DataOutputStream serverWriter;
    private byte NUMBER_OF_VALUES = 3;
    private double[] dataArray;

    // update as these are subject to change, TODO Get finalized data format before final commit
    private double angle, throttle, turn;

    public VisionClient() throws VisionClientInitializationException
    {
        try
        {
            visionSocket = new Socket(Constants.VISION_CLIENT_IP_ADDRESS, Constants.VISION_CLIENT_PORT);
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
    }

    public void run()
    {
        try
        {
            // verify what needs to be written to the vision server
            serverWriter.writeBytes("Can I have some data please?");
            String data = serverReader.readLine();
            parseData(data);
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    private void parseData(String unparsed)
    {
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
        angle = values[0];
        throttle = values[1];
        turn = values[2];
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
