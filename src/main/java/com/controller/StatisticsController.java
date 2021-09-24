package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.animation.Animation;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

import com.model.ChatQuestion;
import com.model.CommonQuestion;
import com.model.YoungAdult;
import com.service.QuestionService;
import com.service.QuestionServiceImpl;
import com.service.RegistrationService;
import com.service.RegistrationServiceImpl;


@Named
@RequestScoped
public class StatisticsController {

	 private BarChartModel barModel;
	 private BarChartModel model;
	 private String showStatistics = "";
	 private RegistrationService regService = new RegistrationServiceImpl();
	 private int count =1;
	 
	 private QuestionService questionService = new QuestionServiceImpl();
	 
	
	 public StatisticsController(){
		showCommonAskedQuestion();
	        createBarModel();
	    }
	 
	 
	 private void createBarModel() {
		
	    }
	 
	 


	public BarChartModel getBarModel() {
		return barModel;
	}


	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public void show() {
		barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Young Adult Statistics");
		count =1;
		if(!showStatistics.equals("")) {
			 List<Number> values = new ArrayList<>();
		List<YoungAdult>list = regService.youngAdultList().stream().filter(i->i.getCreatedDate().getYear()== Integer.valueOf(showStatistics)).collect(Collectors.toList());
		while (count <=12) {
		List<YoungAdult>li=	list.stream().filter(i->i.getCreatedDate().getMonthValue() == count).collect(Collectors.toList());
	        values.add(li.size());
	        barDataSet.setData(values);

	       count++;
		}
		
		 List<String> bgColor = new ArrayList<>();
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        bgColor.add("rgba(54, 162, 235, 235)");
	        barDataSet.setBackgroundColor(bgColor);

	        List<String> borderColor = new ArrayList<>();
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        borderColor.add("rgb(255, 99, 132)");
	        barDataSet.setBorderColor(borderColor);
	        barDataSet.setBorderWidth(1);

	        data.addChartDataSet(barDataSet);
		
		 List<String> labels = new ArrayList<>();
	        labels.add("January");
	        labels.add("February");
	        labels.add("March");
	        labels.add("April");
	        labels.add("May");
	        labels.add("June");
	        labels.add("July");
	        labels.add("August");
	        labels.add("September");
	        labels.add("October");
	        labels.add("November");
	        labels.add("December");
	        data.setLabels(labels);
	        barModel.setData(data);

	        //Options
	        BarChartOptions options = new BarChartOptions();
	        CartesianScales cScales = new CartesianScales();
	        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
	        linearAxes.setOffset(true);
	        CartesianLinearTicks ticks = new CartesianLinearTicks();
	        ticks.setBeginAtZero(true);
	        linearAxes.setTicks(ticks);
	        cScales.addYAxesData(linearAxes);
	        options.setScales(cScales);

	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Bar Chart");
	        options.setTitle(title);

	        Legend legend = new Legend();
	        legend.setDisplay(true);
	        legend.setPosition("top");
	        LegendLabel legendLabels = new LegendLabel();
	        legendLabels.setFontStyle("bold");
	        legendLabels.setFontColor("#2980B9");
	        legendLabels.setFontSize(24);
	        legend.setLabels(legendLabels);
	        options.setLegend(legend);

	        // disable animation
	        Animation animation = new Animation();
	        animation.setDuration(0);
	        options.setAnimation(animation);

	        barModel.setOptions(options);
		
		
		}
	 
	 }
	
	public void showCommonAskedQuestion() {
		
		this.showStatistics ="";
		
		model = new BarChartModel();
		ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Rating Question");
		List<CommonQuestion>categories = regService.categories();
		List<Number> values = new ArrayList<>();
		 List<String> labels = new ArrayList<>();
		 List<String> bgColor = new ArrayList<>();
		 List<String> borderColor = new ArrayList<>();
		 
		 List<CommonQuestion>catego = new ArrayList<CommonQuestion>();
		 
		 for(CommonQuestion q : categories) {
			 List<ChatQuestion>list = questionService.getAll().stream().filter(i->i.getCommonQuestion().getId() == q.getId()).collect(Collectors.toList());
			 if(list.size() == 0) {
				 continue;
			 }else {
				 catego.add(q);
			 }
		 }
	        
		for(CommonQuestion q : catego) {
			List<ChatQuestion>list = questionService.getAll().stream().filter(i->i.getCommonQuestion().getId() == q.getId()).collect(Collectors.toList());
			  values.add(list.size());
		       barDataSet.setData(values);
			//System.out.println(q.getMessage()+ "size---------------"+ list.size());
		       
		      
		        labels.add(q.getMessage());
		       
		        
		        data.setLabels(labels);
		        model.setData(data);
		        
		       
		        bgColor.add("rgba(54, 162, 235, 235)");
		        barDataSet.setBackgroundColor(bgColor);
		        
		       
		        borderColor.add("rgb(255, 99, 132)");
		      
		        barDataSet.setBorderColor(borderColor);
			
		}
		
	        barDataSet.setBorderWidth(1);

	        data.addChartDataSet(barDataSet);
	        //Options
	        BarChartOptions options = new BarChartOptions();
	        CartesianScales cScales = new CartesianScales();
	        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
	        linearAxes.setOffset(true);
	        CartesianLinearTicks ticks = new CartesianLinearTicks();
	        ticks.setBeginAtZero(true);
	        linearAxes.setTicks(ticks);
	        cScales.addYAxesData(linearAxes);
	        options.setScales(cScales);

	        Title title = new Title();
	        title.setDisplay(true);
	        title.setText("Bar Chart");
	        options.setTitle(title);

	        Legend legend = new Legend();
	        legend.setDisplay(true);
	        legend.setPosition("top");
	        LegendLabel legendLabels = new LegendLabel();
	        legendLabels.setFontStyle("bold");
	        legendLabels.setFontColor("#2980B9");
	        legendLabels.setFontSize(24);
	        legend.setLabels(legendLabels);
	        options.setLegend(legend);

	        // disable animation
	        Animation animation = new Animation();
	        animation.setDuration(0);
	        options.setAnimation(animation);

	        model.setOptions(options);
		
	}

	public String getShowStatistics() {
		return showStatistics;
	}


	public void setShowStatistics(String showStatistics) {
		this.showStatistics = showStatistics;
	}


	public BarChartModel getModel() {
		return model;
	}


	public void setModel(BarChartModel model) {
		this.model = model;
	}
	

	
	
	 
}
