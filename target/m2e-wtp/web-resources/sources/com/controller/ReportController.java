package com.controller;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.model.ChatQuestion;
import com.model.ChatQuestion.Category;
import com.model.HealthCare;
import com.model.YoungAdult;
import com.service.QuestionService;
import com.service.QuestionServiceImpl;
import com.service.RegistrationService;
import com.service.RegistrationServiceImpl;
import com.service.Validation;


@Named("reportController")
@RequestScoped
public class ReportController {
	
	
	private QuestionService questionService = new QuestionServiceImpl();
	
	private RegistrationService regService = new RegistrationServiceImpl();
	
	
	
	private Date start=null;
	private Date end = null;
	
	

	
	public void report() {

		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			String fileName = " privateReport.pdf";
			String contentType = "application/pdf";
			ec.responseReset();
			ec.setResponseContentType(contentType);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			OutputStream out = ec.getResponseOutputStream();
			Document doc = new Document();
			PdfWriter.getInstance(doc, out);
			LineSeparator ls = new LineSeparator();
			doc.open();

			Paragraph header = new Paragraph();

			Paragraph header1 = new Paragraph(
					"Rwanda,\nP.O.Box 2029 - Kigali,Rwanda\nPhone : (+250) 787 660 286"
							+ "\nEmail : EMSHR@gmail.com");
			// header.setAlignment(Element.ALIGN_RIGHT);
			header.setAlignment(Image.ALIGN_LEFT + Element.ALIGN_RIGHT);
			header.add(header1);
			doc.add(header);
			doc.add(new Chunk(ls));
			doc.add(new Paragraph("                                          "));

			doc.add(new Paragraph("Date:" + new SimpleDateFormat("dd/MMM/yyyy").format(new Date())));

			Paragraph p = new Paragraph("PRIVATE QUESTION REPORT",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 15, Font.BOLD, BaseColor.DARK_GRAY));
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			doc.add(table);
			// BaseColor color = new BaseColor(10, 113, 156);

			Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);

			PdfPCell namesCell = new PdfPCell(new Phrase("Young Adult\n\n", font0));

			namesCell.setBorder(Rectangle.BOTTOM);
			table.addCell(namesCell);
			
			PdfPCell givenDate = new PdfPCell(new Phrase("HealthCare\n\n", font0));
			givenDate.setBorder(Rectangle.BOTTOM);
			table.addCell(givenDate); 
			
			PdfPCell quantity = new PdfPCell(new Phrase("Question Category\n\n", font0));
			quantity.setBorder(Rectangle.BOTTOM);
			table.addCell(quantity);
			
			PdfPCell assignedBy = new PdfPCell(new Phrase("Question About It\n\n", font0));
			assignedBy.setBorder(Rectangle.BOTTOM);
			table.addCell(assignedBy);
			
			PdfPCell totalCost = new PdfPCell(new Phrase("Request Date\n\n", font0));
			totalCost.setBorder(Rectangle.BOTTOM);
			table.addCell(totalCost);

			for (ChatQuestion re : questionService.getAll().stream().filter(i->i.getCategory() == Category.PRIVATE).collect(Collectors.toList())) {
				PdfPCell id = new PdfPCell(new Phrase(re.getYoungAdult().getFirstName() + " "+ re.getYoungAdult().getLastName()));
				id.setBorder(Rectangle.NO_BORDER);
				table.addCell(id);
				

				PdfPCell issues = new PdfPCell(new Phrase(re.getHealthCare().getFirstName()+" "+re.getHealthCare().getLastName()));
				issues.setBorder(Rectangle.NO_BORDER);
				table.addCell(issues);
				
				if (re.getCommonQuestion() == null) {
					PdfPCell status = new PdfPCell(new Phrase("OTHER"));
					status.setBorder(Rectangle.NO_BORDER);
					table.addCell(status);
				} else {
					PdfPCell status = new PdfPCell(new Phrase(re.getCommonQuestion().getMessage()));
					status.setBorder(Rectangle.NO_BORDER);
					table.addCell(status);
				}

				PdfPCell names = new PdfPCell(
						new Phrase(re.getMessage()));
				names.setBorder(Rectangle.NO_BORDER);
				table.addCell(names);
				
				
				
				
				
				PdfPCell requestDate = new PdfPCell(
						new Phrase(new SimpleDateFormat("yyyy/MM/dd").format(re.getCreatedDate())));
				requestDate.setBorder(Rectangle.NO_BORDER);
				table.addCell(requestDate);

			}

			doc.add(table);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));
			String d = new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(new Date());
			Paragraph printedOn = new Paragraph("Printed On:" + d);
			printedOn.setAlignment(Element.ALIGN_RIGHT);
			doc.add(printedOn);
			doc.close();

			fc.responseComplete();

		} catch (Exception ex) {
			System.err.println("Error:" + ex.getMessage());
			new Validation().errorMessage("Error:", ex.getMessage());
		}
	}
	public void publicReport() {

		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			String fileName = " publicReport.pdf";
			String contentType = "application/pdf";
			ec.responseReset();
			ec.setResponseContentType(contentType);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			OutputStream out = ec.getResponseOutputStream();
			Document doc = new Document();
			PdfWriter.getInstance(doc, out);
			LineSeparator ls = new LineSeparator();
			doc.open();

			Paragraph header = new Paragraph();

			Paragraph header1 = new Paragraph(
					"Rwanda,\nP.O.Box 2029 - Kigali,Rwanda\nPhone : (+250) 787 660 286"
							+ "\nEmail : EMSHR@gmail.com");
			// header.setAlignment(Element.ALIGN_RIGHT);
			header.setAlignment(Image.ALIGN_LEFT + Element.ALIGN_RIGHT);
			header.add(header1);
			doc.add(header);
			doc.add(new Chunk(ls));
			doc.add(new Paragraph("                                          "));

			doc.add(new Paragraph("Date:" + new SimpleDateFormat("dd/MMM/yyyy").format(new Date())));

			Paragraph p = new Paragraph("PUBLIC QUESTION REPORT",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 15, Font.BOLD, BaseColor.DARK_GRAY));
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			doc.add(table);
			// BaseColor color = new BaseColor(10, 113, 156);

			Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);

			PdfPCell namesCell = new PdfPCell(new Phrase("Young Adult\n\n", font0));
			namesCell.setBorder(Rectangle.BOTTOM);
			table.addCell(namesCell);
			
			PdfPCell quantity = new PdfPCell(new Phrase("Question Category\n\n", font0));
			quantity.setBorder(Rectangle.BOTTOM);
			table.addCell(quantity);
			
			PdfPCell assignedBy = new PdfPCell(new Phrase("Message\n\n", font0));
			assignedBy.setBorder(Rectangle.BOTTOM);
			table.addCell(assignedBy);
			
			
			
			PdfPCell totalCost = new PdfPCell(new Phrase("Request Date\n\n", font0));
			totalCost.setBorder(Rectangle.BOTTOM);
			table.addCell(totalCost);

			for (ChatQuestion re : questionService.getAll().stream().filter(i->i.getCategory() == Category.PUBLIC).collect(Collectors.toList())) {
				PdfPCell id = new PdfPCell(new Phrase(re.getYoungAdult().getFirstName() + " "+ re.getYoungAdult().getLastName()));
				id.setBorder(Rectangle.NO_BORDER);
				table.addCell(id);


				if (re.getCommonQuestion() == null) {
					PdfPCell status = new PdfPCell(new Phrase("OTHER"));
					status.setBorder(Rectangle.NO_BORDER);
					table.addCell(status);
				} else {
					PdfPCell status = new PdfPCell(new Phrase(re.getCommonQuestion().getMessage()));
					status.setBorder(Rectangle.NO_BORDER);
					table.addCell(status);
				}
				
				PdfPCell names = new PdfPCell(
						new Phrase(re.getMessage()));
				names.setBorder(Rectangle.NO_BORDER);
				table.addCell(names);
				
				
				PdfPCell requestDate = new PdfPCell(
						new Phrase(new SimpleDateFormat("yyyy/MM/dd").format(re.getCreatedDate())));
				requestDate.setBorder(Rectangle.NO_BORDER);
				table.addCell(requestDate);

			}

			doc.add(table);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));
			String d = new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(new Date());
			Paragraph printedOn = new Paragraph("Printed On:" + d);
			printedOn.setAlignment(Element.ALIGN_RIGHT);
			doc.add(printedOn);
			doc.close();

			fc.responseComplete();

		} catch (Exception ex) {
			System.err.println("Error:" + ex.getMessage());
			new Validation().errorMessage("Error:", ex.getMessage());
		}
	}
	
	public void youngAdultReport() {
		
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			String fileName = " youngAdult.pdf";
			String contentType = "application/pdf";
			ec.responseReset();
			ec.setResponseContentType(contentType);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			OutputStream out = ec.getResponseOutputStream();
			Document doc = new Document();
			PdfWriter.getInstance(doc, out);
			LineSeparator ls = new LineSeparator();
			doc.open();

			Paragraph header = new Paragraph();

			Paragraph header1 = new Paragraph(
					"Rwanda,\nP.O.Box 2029 - Kigali,Rwanda\nPhone : (+250) 787 660 286"
							+ "\nEmail : EMSHR@gmail.com");
			// header.setAlignment(Element.ALIGN_RIGHT);
			header.setAlignment(Image.ALIGN_LEFT + Element.ALIGN_RIGHT);
			header.add(header1);
			doc.add(header);
			doc.add(new Chunk(ls));
			doc.add(new Paragraph("                                          "));

			doc.add(new Paragraph("Date:" + new SimpleDateFormat("dd/MMM/yyyy").format(new Date())));

			Paragraph p = new Paragraph("YOUNG ADULT REPORT",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 15, Font.BOLD, BaseColor.DARK_GRAY));
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));

			PdfPTable table = new PdfPTable(4);
			table.setWidthPercentage(100);
			doc.add(table);
			// BaseColor color = new BaseColor(10, 113, 156);

			Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);

			PdfPCell namesCell = new PdfPCell(new Phrase("FIRST NAME\n\n", font0));
			namesCell.setBorder(Rectangle.BOTTOM);
			table.addCell(namesCell);
			
			PdfPCell quantity = new PdfPCell(new Phrase("LAST NAME\n\n", font0));
			quantity.setBorder(Rectangle.BOTTOM);
			table.addCell(quantity);
			
			PdfPCell assignedBy = new PdfPCell(new Phrase("PHONE NUMBER\n\n", font0));
			assignedBy.setBorder(Rectangle.BOTTOM);
			table.addCell(assignedBy);
			
			
			
			PdfPCell totalCost = new PdfPCell(new Phrase("CREATED DATE\n\n", font0));
			totalCost.setBorder(Rectangle.BOTTOM);
			table.addCell(totalCost);
			
			LocalDate sdate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate edate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			List<YoungAdult>list = regService.youngAdultList().stream().filter(i->i.getCreatedDate().isAfter(sdate) && i.getCreatedDate().isBefore(edate)).
					collect(Collectors.toList());

			for (YoungAdult re : list) {
				PdfPCell id = new PdfPCell(new Phrase(re.getFirstName()));
				id.setBorder(Rectangle.NO_BORDER);
				table.addCell(id);

					PdfPCell status = new PdfPCell(new Phrase(re.getLastName()));
					status.setBorder(Rectangle.NO_BORDER);
					table.addCell(status);
				
				
				PdfPCell names = new PdfPCell(
						new Phrase(re.getPhoneNumber()));
				names.setBorder(Rectangle.NO_BORDER);
				table.addCell(names);
				
				
				PdfPCell requestDate = new PdfPCell(
						new Phrase(re.getCreatedDate()+ ""));
				requestDate.setBorder(Rectangle.NO_BORDER);
				table.addCell(requestDate);

			}

			doc.add(table);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));
			String d = new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(new Date());
			Paragraph printedOn = new Paragraph("Printed On:" + d);
			printedOn.setAlignment(Element.ALIGN_RIGHT);
			doc.add(printedOn);
			doc.close();

			fc.responseComplete();

		} catch (Exception ex) {
			System.err.println("Error:" + ex.getMessage());
			new Validation().errorMessage("Error:", ex.getMessage());
		}
		
	}
	
public void healthCareReport() {
		
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			String fileName = " healthCare.pdf";
			String contentType = "application/pdf";
			ec.responseReset();
			ec.setResponseContentType(contentType);
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			OutputStream out = ec.getResponseOutputStream();
			Document doc = new Document();
			PdfWriter.getInstance(doc, out);
			LineSeparator ls = new LineSeparator();
			doc.open();

			Paragraph header = new Paragraph();

			Paragraph header1 = new Paragraph(
					"Rwanda,\nP.O.Box 2029 - Kigali,Rwanda\nPhone : (+250) 787 660 286"
							+ "\nEmail : EMSHR@gmail.com");
			// header.setAlignment(Element.ALIGN_RIGHT);
			header.setAlignment(Image.ALIGN_LEFT + Element.ALIGN_RIGHT);
			header.add(header1);
			doc.add(header);
			doc.add(new Chunk(ls));
			doc.add(new Paragraph("                                          "));

			doc.add(new Paragraph("Date:" + new SimpleDateFormat("dd/MMM/yyyy").format(new Date())));

			Paragraph p = new Paragraph("HEARTH CARE REPORT",
					FontFactory.getFont(FontFactory.TIMES_BOLD, 15, Font.BOLD, BaseColor.DARK_GRAY));
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));

			PdfPTable table = new PdfPTable(5);
			table.setWidthPercentage(100);
			doc.add(table);
			// BaseColor color = new BaseColor(10, 113, 156);

			Font font0 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.BLACK);

			PdfPCell namesCell = new PdfPCell(new Phrase("FIRST NAME\n\n", font0));
			namesCell.setBorder(Rectangle.BOTTOM);
			table.addCell(namesCell);
			
			PdfPCell quantity = new PdfPCell(new Phrase("LAST NAME\n\n", font0));
			quantity.setBorder(Rectangle.BOTTOM);
			table.addCell(quantity);
			
			PdfPCell assignedBy = new PdfPCell(new Phrase("PHONE NUMBER\n\n", font0));
			assignedBy.setBorder(Rectangle.BOTTOM);
			table.addCell(assignedBy);
			
			PdfPCell title = new PdfPCell(new Phrase("TITLE\n\n", font0));
			title.setBorder(Rectangle.BOTTOM);
			table.addCell(title);
			
			
			
			PdfPCell totalCost = new PdfPCell(new Phrase("NUMBER OF QUESTION\n\n", font0));
			totalCost.setBorder(Rectangle.BOTTOM);
			table.addCell(totalCost);
			
			
			

			for (HealthCare re : regService.healthCareList()) {
				
				List<ChatQuestion> count = questionService.getAll().stream().filter(i->i.getHealthCare() != null && i.getHealthCare().getNationalId().equals(re.getNationalId())).collect(Collectors.toList());
				PdfPCell id = new PdfPCell(new Phrase(re.getFirstName()));
				id.setBorder(Rectangle.NO_BORDER);
				table.addCell(id);

					PdfPCell status = new PdfPCell(new Phrase(re.getLastName()));
					status.setBorder(Rectangle.NO_BORDER);
					table.addCell(status);
				
				
				PdfPCell names = new PdfPCell(
						new Phrase(re.getPhoneNumber()));
				names.setBorder(Rectangle.NO_BORDER);
				table.addCell(names);
				
				PdfPCell ti = new PdfPCell(
						new Phrase(re.getTitle()));
				ti.setBorder(Rectangle.NO_BORDER);
				table.addCell(ti);
				
				
				PdfPCell requestDate = new PdfPCell(
						new Phrase(count.size()+" "));
				requestDate.setBorder(Rectangle.NO_BORDER);
				table.addCell(requestDate);

			}

			doc.add(table);
			doc.add(new Paragraph("                                          "));
			doc.add(new Paragraph("                                          "));
			String d = new SimpleDateFormat("dd/MMM/yyyy HH:mm").format(new Date());
			Paragraph printedOn = new Paragraph("Printed On:" + d);
			printedOn.setAlignment(Element.ALIGN_RIGHT);
			doc.add(printedOn);
			doc.close();

			fc.responseComplete();

		} catch (Exception ex) {
			System.err.println("Error:" + ex.getMessage());
			new Validation().errorMessage("Error:", ex.getMessage());
		}
		
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	
	
}
