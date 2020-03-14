package xyz.nasaknights.infiniterecharge.util.lighting;


public class IndicatorLightUtil
{

    public enum LightColor
    {

        BLACK(0.99), PURPLE(0.91), GREEN(0.77), DARK_GREEN(0.75), RED(0.61), PURPLE_STROBE(0.35), // This could be subject to change as it strobes the Color 2 option
        GOLD_STROBE(-0.07), LAVA_WAVES(-0.39), LARGE_FIRE(-0.57), LAVA_RAINBOW(-0.93);

        private double output;

        LightColor(double output)
        {
            this.output = output;
        }

        public double getOutput()
        {
            return output;
        }
    }
}
