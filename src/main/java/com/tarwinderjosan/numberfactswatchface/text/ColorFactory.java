package com.tarwinderjosan.numberfactswatchface.text;

import android.graphics.Color;

import com.tarwinderjosan.numberfactswatchface.R;
import com.tarwinderjosan.numberfactswatchface.debug.DebugManager;
import com.tarwinderjosan.numberfactswatchface.util.Utils;

public class ColorFactory {

    // Colors of the balloon corresponding to the last digit of the minute
    // index 0, e.g would be color for 40
    // index 1 e.g would be color for 11
    // index 2 e.g would be color for 52
    private static String[] colors =
            {"#fcae88", "#ff0046", "#1cc4e1", "#1e0d41", "#06d388", "#fcae88", "#fb0045", "#1dc5e2",
            "#1d0c40", "#00d083"};

    private static int[] staticColors = new int[5];

    static {
        int[] colors = new int[5];
        colors[0] = Utils.CONTEXT.getResources().getColor(R.color.green);
        colors[1] = Utils.CONTEXT.getResources().getColor(R.color.brown);
        colors[2] = Utils.CONTEXT.getResources().getColor(R.color.pink);
        colors[3] = Utils.CONTEXT.getResources().getColor(R.color.green);
        colors[4] = Utils.CONTEXT.getResources().getColor(R.color.purple);
        staticColors = colors;

    }

    /**
     * Get a bckrnd color.
     * For people who don't have a fact pact.
     * Only possible to the people who've yet to open the companion app.
     */
    public static int getColorNoPack(char c) {
        if(c == '0' || c == '5') {
            // green
            return Utils.CONTEXT.getResources().getColor(R.color.greenB);
        }
        else if (c == '1' || c == '6') {
            // brown
            return Utils.CONTEXT.getResources().getColor(R.color.brownB);
        } else if (c == '3' || c == '8') {
            // blue
            return Utils.CONTEXT.getResources().getColor(R.color.blueB);
        } else if (c == '4' || c == '9') {
            // Purple
            return Utils.CONTEXT.getResources().getColor(R.color.purpleB);
        } else if (c == '2' || c == 7) {
            // red
            return Utils.CONTEXT.getResources().getColor(R.color.redB);
        }

        return Utils.CONTEXT.getResources().getColor(R.color.redB);
    }

    /**
     * Return appropriate color for the balloon with the current minute;
     * @param minute The current minute
     * @return Color for the current minute.
     */
    public static int getBalloonColor(int minute) {
        String m = String.format("%02d", minute);
        char val = m.charAt(1);
        return Color.parseColor(colors[Integer.parseInt(Character.toString(val)) ] );
    }


}
