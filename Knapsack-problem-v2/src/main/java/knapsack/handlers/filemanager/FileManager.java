/*
 * Copyright (c) 2022 Marcel Gheorghe Becheanu
 * All rights reserved.
 *
 * This code is proprietary and cannot be copied or distributed without the express written permission of Marcel Gheorghe Becheanu.
 */

package knapsack.handlers.filemanager;

import knapsack.handlers.datamanager.DataManager;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;

public class FileManager {

    /**
     * Loads the data from the file at the given directory.
     *
     * @param dir The directory of the file to load
     * @param manager The Manager of data
     * @return true if the file was successfully loaded, false otherwise
     */
    public static boolean load(String dir, DataManager manager){
        if(dir == null)
            return false;

        try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
            // Read the number of items and initialize the values and weights arrays
            manager.setSize(Integer.parseInt(br.readLine()));
            int[][] items = new int[manager.getSize()][2];

            // Read the maximum weight
            manager.setMaxWeight(Integer.parseInt(br.readLine()));

            // Read the values and weights of each item
            for (int i = 0; i < manager.getSize(); i++) {
                String[] itemData = br.readLine().split(" ");
                items[i][0] = Integer.parseInt(itemData[0]);
                items[i][1] = Integer.parseInt(itemData[1]);
            }
            manager.setItems(items);

            // Read the ideal value
            manager.setIdealValue(Integer.parseInt(br.readLine()));
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
            return false;
        }

        System.out.println("The data from " + dir + " is loaded");
        System.out.println();
        return true;
    }

    public static boolean exists(String dir){
        try {
            return new File(dir).exists();
        } catch (FileSystemNotFoundException e){
            return false;
        }
    }
}
