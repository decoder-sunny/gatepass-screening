package com.gatepass.main.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import com.gatepass.main.model.Questions;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class TemplatePDF {
	
	

	public ByteArrayInputStream generatePDFReport(List<Questions> questions,String template_name,String link) {
		
		//Create layout and set background & borders
		Rectangle layout = new Rectangle(PageSize.A4);
		layout.setBorderColor(BaseColor.DARK_GRAY);  //Border color
		layout.setBorderWidth(6);      //Border width  
		layout.setBorder(Rectangle.BOX);  //Border on 4 sides
		Document document = new Document(layout);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();

			LineSeparator ls = new LineSeparator();

			// Add Header ->
			Paragraph header1 = new Paragraph(template_name, 
					FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK));
			header1.setAlignment(Element.ALIGN_CENTER);
			document.add(header1);
			document.add(new Chunk(ls));

			for (int i = 0; i < questions.size(); i++) {
				Paragraph ques = new Paragraph("Question "+(i+1)+": "+
						questions.get(i).getQuestion().replaceAll("\\<[^>]*>",""), 
						FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK));
				ques.setAlignment(Element.ALIGN_LEFT);
				document.add(ques);
				if(Arrays.asList(3,4).contains(questions.get(i).getQuestionType().getId())) {
					System.out.println("here");
					if(questions.get(i).getQuestionImage()!=null) {
						PdfPTable table = new PdfPTable(1);
						table.setWidthPercentage(100);
					    table.addCell(getCellWithImage(questions.get(i).getQuestionImage().getQuestionImage(),link));
						document.add(table);
					}
					
				}
				document.add(Chunk.NEWLINE);
				if(Arrays.asList(1,3).contains(questions.get(i).getQuestionType().getId())) {
					PdfPTable table = new PdfPTable(4);
					table.setWidthPercentage(100);
					table.addCell(getCell("Option A : "+questions.get(i).getOptionsObjective().getOptionA(), PdfPCell.ALIGN_LEFT));
					table.addCell(getCell("Option B : "+questions.get(i).getOptionsObjective().getOptionB(), PdfPCell.ALIGN_LEFT));
					table.addCell(getCell("Option C : "+questions.get(i).getOptionsObjective().getOptionC(), PdfPCell.ALIGN_LEFT));
					table.addCell(getCell("Option D : "+questions.get(i).getOptionsObjective().getOptionD(), PdfPCell.ALIGN_LEFT));
					document.add(table);
				}	
				if(Arrays.asList(2,4).contains(questions.get(i).getQuestionType().getId())) {
					PdfPTable table = new PdfPTable(4);
					table.setWidthPercentage(100);
					table.addCell(getCellWithImage(questions.get(i).getOptionsImage().getOptionAImage(),link));
					table.addCell(getCellWithImage(questions.get(i).getOptionsImage().getOptionBImage(),link));
					table.addCell(getCellWithImage(questions.get(i).getOptionsImage().getOptionCImage(),link));
					table.addCell(getCellWithImage(questions.get(i).getOptionsImage().getOptionDImage(),link));
					document.add(table);
				}	
				document.add(Chunk.NEWLINE);
			}

			document.close();
		}catch(DocumentException e) {
			e.printStackTrace();
		} 

		return new ByteArrayInputStream(out.toByteArray());
	}

	public PdfPCell getCell(String text, int alignment) {
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK);
		PdfPCell cell = new PdfPCell(new Phrase(text,font));
		cell.setPadding(0);	    
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public PdfPCell getCellWithImage(String image,String url) {		
		try {			
			String imageUrl = url+image;
			Image img = Image.getInstance(new URL(imageUrl));
			PdfPCell cell = new PdfPCell(img, true);
			cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
			cell.setBorder(PdfPCell.NO_BORDER);				       
		    return cell;
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}

