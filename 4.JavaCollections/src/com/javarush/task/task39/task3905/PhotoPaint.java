package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        if (image.length  <= x || image[0].length <= y || image[y][x].equals(desiredColor)) return false;
        else {
            Color colorOld = image[y][x];
            setNeighborColor(image, x, y, desiredColor, colorOld);
            image[y][x] = desiredColor;
            return true;
        }
    }

    private void setNeighborColor(Color[][] image, int x, int y, Color desiredColor, Color colorOld) {
        for (int i = 0; i < image.length ; i++) {
            for (int j = 0; j < image[i].length; j++) {
                if (image[j][i].equals(colorOld)) {
                    if ((i == x && j + 1 == y) || (i == x && j - 1 == y) ) {
                        image[j][i] = desiredColor;
                    } else if ((j == j && i + 1 == x) || (j == y && i - 1 == x) ){
                        image[j][i] = desiredColor;
                    }
                }
            }
        }
    }
}
