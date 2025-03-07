package org.djago.util;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;

/**
 * Examples of various different ways to print PDFs using PDFBox.
 */
public class Printing
{
    /**
     * Entry point.
     */
//    public static void main(String args[]) throws PrinterException, IOException
//    {
//    	
//       com.djago
//        PDDocument document = PDDocument.load(new File("/Users/youssoufdiarra/Desktop/output.pdf"));
//        
//        // choose your printing method:
//        print(document); 
//        //printWithAttributes(document);
//        //printWithDialog(document);
//        //printWithDialogAndAttributes(document);
//        //printWithPaper(document);
//    }

    /**
     * Prints the document at its actual size. This is the recommended way to print.
     */
    public static void print(PDDocument document) throws IOException, PrinterException
    {
    	System.out.println("Youssouf is printing");
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));
        job.print();
    }

    /**
     * Prints using custom PrintRequestAttribute values.
     */
    private static void printWithAttributes(PDDocument document)
            throws IOException, PrinterException
    {
    	
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1

        job.print(attr);
    }

    /**
     * Prints with a print preview dialog.
     */
    private static void printWithDialog(PDDocument document) throws IOException, PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        if (job.printDialog())
        {
            job.print();
        }
    }

    /**
     * Prints with a print preview dialog and custom PrintRequestAttribute values.
     */
    private static void printWithDialogAndAttributes(PDDocument document)
            throws IOException, PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
        attr.add(new PageRanges(1, 1)); // pages 1 to 1

        if (job.printDialog(attr))
        {
            job.print(attr);
        }
    }
    
    /**
     * Prints using a custom page size and custom margins.
     */
    private static void printWithPaper(PDDocument document)
            throws IOException, PrinterException
    {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(document));

        // define custom paper
        Paper paper = new Paper();
        paper.setSize(306, 396); // 1/72 inch
        paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight()); // no margins

        // custom page format
        PageFormat pageFormat = new PageFormat();
        pageFormat.setPaper(paper);
        
        // override the page format
        Book book = new Book();
        book.append(new PDFPrintable(document), pageFormat);
        job.setPageable(book);
        
        job.print();
    }
}