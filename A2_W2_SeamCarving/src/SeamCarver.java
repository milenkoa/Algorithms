/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author milenko
 */

import java.awt.Color;

public class SeamCarver {
   private Picture pic;
   
//   private double[][] energy;
//   private double[][] distance;
//   private int[][] previousPixel;
   
   
   public SeamCarver(Picture picture) {
       pic = new Picture(picture);
       
   }
   
   // current picture
   public Picture picture()  {
       return pic;
   }       
   
   // width  of current picture
   public int width() { 
       return pic.width();
   }
   
   // height of current picture
   public int height() {
       return pic.height();
   }
   
   // energy of pixel at column x and row y in current picture
   public  double energy(int x, int y)  {
       if (x < 0) {
           throw new IndexOutOfBoundsException();
       }
       if (y < 0) {
           throw new IndexOutOfBoundsException();
       }
       if (x >= width()) {
           throw new IndexOutOfBoundsException();
       }
       if (y >= height()) {
           throw new IndexOutOfBoundsException();
       }
       
       // We define the energy of pixels at the border 
       // of the image to be 2552 + 2552 + 2552 = 195075
       if (x == 0 || y == 0 || x == (width()-1) || y == (height()-1)) {
           return 195075;
       }
       Color cx1 = pic.get(x-1, y);
       Color cx2 = pic.get(x+1, y);
       Color cy1 = pic.get(x, y-1);
       Color cy2 = pic.get(x, y+1);
       
       int rx = Math.abs(cx1.getRed() - cx2.getRed());
       int gx = Math.abs(cx1.getGreen() - cx2.getGreen());
       int bx = Math.abs(cx1.getBlue() - cx2.getBlue());
       
       int ry = Math.abs(cy1.getRed() - cy2.getRed());
       int gy = Math.abs(cy1.getGreen() - cy2.getGreen());
       int by = Math.abs(cy1.getBlue() - cy2.getBlue());
       
       double powOfDeltax = rx*rx + gx*gx + bx*bx;
       double powOfDeltay = ry*ry + gy*gy + by*by;
       
       return (powOfDeltax + powOfDeltay);
   }
   
   // sequence of indices for horizontal seam in current picture
   public int[] findHorizontalSeam()  { 
       double[][] energy = new double[width()][height()];
       double[][] distance = new double[width()][height()];
       int[][] previousPixel = new int[width()][height()];
       
       int[] result = new int[width()];
  
       //StdOut.println("FindVerticalSeam computing energy");
       for (int i = 0; i < width(); i++) {           
           for (int j = 0; j < height(); j++) {
               energy[i][j] = energy(i, j);
           }
       }   
       //StdOut.println("findVerticalSeam computing energy DONE");
       
       for (int j = 0; j < height(); j++) {
           distance[0][j] = energy[0][j];     
       }
       
       for (int i = 1; i < width(); i++) {
           for (int j = 0; j < height(); j++) {
               //choose previous pixel with minimum distance
               //choose min distance among (i-1, j-1), (i-1, j), and (i-1, j+1)
               if (j == 0) {
                   //choose min distance among (i-1, j) and (i-1, j+1)
                   if (distance[i-1][j] <= distance[i-1][j+1]) {
                       distance[i][j] = distance[i-1][j] + energy[i][j];
                       previousPixel[i][j] = j;                   
                   }
                   else {
                       distance[i][j] = distance[i-1][j+1] + energy[i][j];
                       previousPixel[i][j] = j+1;     
                   }
               } else if (j == height()-1) {
                   //choose min distance among (i-1, j-1) and (i-1, j)
                   if (distance[i-1][j-1] <= distance[i-1][j]) {
                       distance[i][j] = distance[i-1][j-1] + energy[i][j];
                       previousPixel[i][j] = j-1;                   
                   }
                   else {
                       distance[i][j] = distance[i-1][j] + energy[i][j];
                       previousPixel[i][j] = j;     
                   }  
               } else {
                   //choose min distance among (i-1, j-1), (i-1, j), and (i-1, j+1)
                   if (distance[i-1][j] <= distance[i-1][j+1]) {
                       if (distance[i-1][j-1] <= distance[i-1][j]) {
                           //min energy (i-1, j-1)
                           distance[i][j] = distance[i-1][j-1] + energy[i][j];
                           previousPixel[i][j] = j-1;          
                       }
                       else {
                           //min energy (i-1, j)
                           distance[i][j] = distance[i-1][j] + energy[i][j];
                           previousPixel[i][j] = j;           
                       }          
                   }
                   else {
                       if (distance[i-1][j-1] <= distance[i-1][j+1]) {
                           //min energy (i-1, j-1)
                           distance[i][j] = distance[i-1][j-1] + energy[i][j];
                           previousPixel[i][j] = j-1;          
                       }
                       else {
                           //min energy (i-1, j+1)
                           distance[i][j] = distance[i-1][j+1] + energy[i][j];
                           previousPixel[i][j] = j+1;           
                       }    
                       
                   }
               }      
           }           
       }           
       //StdOut.println("findVerticalSeam computing multiple source shortest path DONE");
       
       double minDistance = distance[width()-1][0];
       int minj = 0;
       for (int j = 1; j < height(); j++) {
           if (distance[width()-1][j] < minDistance) {
               minDistance = distance[width()-1][j];
               minj = j;
           }
       }
       
       //StdOut.println("min_j = " + minj);
       result[width()-1] = minj;
       
       for (int k = width()-1; k > 0; k--) {
           //StdOut.println("findVerticalSeam processing result... k = " + k + "; result = " + result[k]);
           result[k-1] = previousPixel[k][result[k]];
       }
       
       return result;
    }
   
   // sequence of indices for vertical   seam in current picture
   public int[] findVerticalSeam()  { 
       double[][] energy = new double[width()][height()];
       //energy = new double[width()][height()];
       double[][] distance = new double[width()][height()];
       //distance = new double[width()][height()];
       int[][] previousPixel = new int[width()][height()];
       //previousPixel = new int[width()][height()];
       
       int[] result = new int[height()];
  
       //StdOut.println("FindVerticalSeam computing energy");
       for (int i = 0; i < width(); i++) {           
           for (int j = 0; j < height(); j++) {
               energy[i][j] = energy(i, j);
           }
       }   
       //StdOut.println("findVerticalSeam computing energy DONE");
       
       for (int i = 0; i < width(); i++) {
           distance[i][0] = energy[i][0];     
       }
       
       for (int j = 1; j < height(); j++) {
           for (int i = 0; i < width(); i++) {
               //choose previous pixel with minimum distance
               //choose min distance among (i-1, j-1), (i, j-1), and (i+1, j-1)
               if (i == 0) {
                   //choose min distance among (i, j-1), and (i+1, j-1)
                   if (distance[i][j-1] <= distance[i+1][j-1]) {
                       distance[i][j] = distance[i][j-1] + energy[i][j];
                       previousPixel[i][j] = i;                   
                   }
                   else {
                       distance[i][j] = distance[i+1][j-1] + energy[i][j];
                       previousPixel[i][j] = i+1;     
                   }
               } else if (i == width()-1) {
                   //choose min distance among (i-1, j-1) and (i, j-1)
                   if (distance[i-1][j-1] <= distance[i][j-1]) {
                       distance[i][j] = distance[i-1][j-1] + energy[i][j];
                       previousPixel[i][j] = i-1;                   
                   }
                   else {
                       distance[i][j] = distance[i][j-1] + energy[i][j];
                       previousPixel[i][j] = i;     
                   }  
               } else {
                   //choose min distance among (i-1, j-1), (i, j-1), and (i+1, j-1)
                   if (distance[i][j-1] <= distance[i+1][j-1]) {
                       if (distance[i-1][j-1] <= distance[i][j-1]) {
                           //min energy (i-1, j-1)
                           distance[i][j] = distance[i-1][j-1] + energy[i][j];
                           previousPixel[i][j] = i-1;          
                       }
                       else {
                           //min energy (i, j-1)
                           distance[i][j] = distance[i][j-1] + energy[i][j];
                           previousPixel[i][j] = i;           
                       }          
                   }
                   else {
                       if (distance[i-1][j-1] <= distance[i+1][j-1]) {
                           //min energy (i-1, j-1)
                           distance[i][j] = distance[i-1][j-1] + energy[i][j];
                           previousPixel[i][j] = i-1;          
                       }
                       else {
                           //min energy (i+1, j-1)
                           distance[i][j] = distance[i+1][j-1] + energy[i][j];
                           previousPixel[i][j] = i+1;           
                       }    
                       
                   }
               }      
           }           
       }
       
       double minDistance = distance[0][height()-1];
       int mini = 0;
       for (int i = 1; i < width(); i++) {
           if (distance[i][height()-1] < minDistance) {
               minDistance = distance[i][height()-1];
               mini = i;
           }
       }
       
       //StdOut.println("min_i = " + mini);
       result[height()-1] = mini;
       
       for (int k = height()-1; k > 0; k--) {
           //StdOut.println("findVerticalSeam processing result... k = " + k + "; result = " + result[k]);
           result[k-1] = previousPixel[result[k]][k];
       }
       
       return result;
    }
           
   
   // remove horizontal seam from current picture
   public void removeHorizontalSeam(int[] a)  {
       // test if array parameter is wrong length
       if (a.length != width()) {
           throw new RuntimeException();
       }
       Picture newPic = new Picture(width(), height()-1);
       for (int i = 0; i < width(); i++) {
           boolean bRemoved = false;
           for (int j = 0; j < height(); j++) {
               //StdOut.println("i = " + i + ";j = " + j);
               if (j == a[i]) {
                   //StdOut.println("removing a pixel");
                   bRemoved = true;
                   continue;
               }
               if (!bRemoved) {
                   newPic.set(i, j, pic.get(i, j));
               }
               else {
                   newPic.set(i, j-1, pic.get(i, j));
               }
           }
       }
       pic = newPic;
      
   }
   
   // remove vertical   seam from current picture
   public void removeVerticalSeam(int[] a)  { 
       if (a.length != height()) {
           throw new RuntimeException();
       }
       Picture newPic = new Picture(width()-1, height());
       for (int j = 0; j < height(); j++) {
           boolean bRemoved = false;
           for (int i = 0; i < width(); i++) {
               // skip i when a[j] == i
               if  (i == a[j]) {
                   bRemoved = true;
                   continue;
               }
               if (!bRemoved) {
                   newPic.set(i, j, pic.get(i, j));        
               }
               else {
                   newPic.set(i-1, j, pic.get(i, j));          
               }
           }
       }
       pic = newPic;
   }   
}
