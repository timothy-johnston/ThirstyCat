package com.tj.thirstyCat.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.service.ImageService;

@RunWith(MockitoJUnitRunner.class)
public class ImageControllerTest {

	@InjectMocks
	ImageController ImageController;
	
	@Mock
	ImageService ImageService;
	
	private Long testDrinkId = 1L;
	
	@Test
	public void newImageEndpointCallsAddImageService() {
		
		Image testImage = new Image(testDrinkId, new byte[] {});
		
		ImageController.persistImage(testImage);
		
		verify(ImageService, times(1)).addImage(testImage);
		
	}
	
	@Test
	public void retrieveLastImageEndpointCallsRetriveLastImageServiceAndReturnsImage() {
		
		Date startDate = new Date();
		Date endDate = new Date();
		Image expectedImage = new Image(testDrinkId, new byte[] {});
		when(ImageService.getLastImage()).thenReturn(expectedImage);
		
		Image returnedImage = ImageController.retrieveLastImage();
		
		verify(ImageService, times(1)).getLastImage();
		assertEquals(expectedImage, returnedImage);

	}
	
	@Test
	public void retrieveAllImagesEndpointCallsRetriveAllImagesServiceAndReturnsAllImages() {
		
		List<Image> expectedImages = new ArrayList<Image>();
		Image expected1 = new Image();
		Image expected2 = new Image(testDrinkId, null);
		Image expected3 = new Image(null, new byte[] {});
		expectedImages.add(expected1);
		expectedImages.add(expected2);
		expectedImages.add(expected3);
		when(ImageService.getAllImages()).thenReturn(expectedImages);
		
		List<Image> returnedImages = ImageController.getAllImages();
		
		verify(ImageService, times(1)).getAllImages();
		assertEquals(expectedImages, returnedImages);
		
	}
	
	
	@Test
	public void retrieveImage_ID_EndpointCallsRetrieveImage_ID_Service() {

		Image expectedImage = new Image(testDrinkId, new byte[] {});
		int ImageId = 3;
		when(ImageService.getImageById(ImageId)).thenReturn(expectedImage);
		
		Image returnedImage = ImageController.getImage(ImageId);
		
		verify(ImageService, times(1)).getImageById(ImageId);
		assertEquals(expectedImage, returnedImage);
		
	}

}
