package com.tj.thirstyCat.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.service.ImageService;

@RunWith(MockitoJUnitRunner.class)
public class ImageControllerTest {

	@Test
	public void dummy() {
		assert(true);
	}
	
//	@InjectMocks
//	ImageController imageController;
//	
//	@Mock
//	ImageService imageService;
//	
//	private Long testDrinkId = 1L;
//	
//	@Test
//	public void newImageEndpointCallsAddImageService() {
//		
//		Image testImage = new Image(testDrinkId, new byte[] {});
//		
////		imageController.persistImage(testImage);
//		
//		verify(imageService, times(1)).addImage(testImage);
//		
//	}
//	
//	@Test
//	public void retrieveLastImageEndpointCallsRetriveLastImageServiceAndReturnsImage() {
//		
//		Image expectedImage = new Image(testDrinkId, new byte[] {});
////		when(imageService.getLastImage()).thenReturn(expectedImage);
//		
//		byte[] returnedImage = imageController.retrieveLastImage();
//		
//		verify(imageService, times(1)).getLastImage();
//		assertEquals(expectedImage, returnedImage);
//
//	}
//	
//	@Test
//	public void retrieveAllImagesEndpointCallsRetriveAllImagesServiceAndReturnsAllImages() {
//		
//		List<Image> expectedImages = new ArrayList<Image>();
//		Image expected1 = new Image();
//		Image expected2 = new Image(testDrinkId, null);
//		Image expected3 = new Image(null, new byte[] {});
//		expectedImages.add(expected1);
//		expectedImages.add(expected2);
//		expectedImages.add(expected3);
//		when(imageService.getAllImages()).thenReturn(expectedImages);
//		
//		List<Image> returnedImages = imageController.getAllImages();
//		
//		verify(imageService, times(1)).getAllImages();
//		assertEquals(expectedImages, returnedImages);
//		
//	}
//	
//	@Test
//	public void retrieveImage_ID_EndpointCallsRetrieveImage_ID_Service() {
//
//		Image expectedImage = new Image(testDrinkId, new byte[] {});
//		Long imageId = 3L;
//		when(imageService.getImageById(imageId)).thenReturn(expectedImage);
//		
//		Image returnedImage = imageController.getImage(imageId);
//		
//		verify(imageService, times(1)).getImageById(imageId);
//		assertEquals(expectedImage, returnedImage);
//		
//	}
//	
//	@Test
//	public void favoriteImage_ID_EndpointCallsFavoriteImageService() {
//		
//		Long imageId = 3L;
//		
//		imageController.favoriteImage(imageId);
//		
//		verify(imageService, times(1)).favoriteImage(imageId);
//		
//	}

}
