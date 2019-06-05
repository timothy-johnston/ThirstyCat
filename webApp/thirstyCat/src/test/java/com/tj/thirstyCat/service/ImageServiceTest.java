package com.tj.thirstyCat.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tj.thirstyCat.model.Image;
import com.tj.thirstyCat.repository.ImageRepository;

@RunWith(MockitoJUnitRunner.class)
public class ImageServiceTest {

	@InjectMocks
	ImageService imageService;
	
	@Mock
	ImageRepository imageRepository;
	
	private Long testDrinkId = 1L;

	@Test
	public void addImageCallsRepositorySaveMethod() {
		
		Image testImage = new Image(testDrinkId, new byte[] {});
		
		imageService.addImage(testImage);
		
		verify(imageRepository, times(1)).save(testImage);
		
	}
	
//	@Test
//	public void getLastImageGetsLastImageAddedToDatabase() {
//		
//		Date startDate = new Date();
//		Date endDate = new Date();
//		Image firstAddedImage = new Image(startDate, endDate);
//		Image secondAddedImage = new Image(null, null);
//		
//	}
	
	@Test
	public void getAllImagesReturnsListOfAllImages() {
		
		List<Image> expectedImages = new ArrayList<Image>();
		Image expected1 = new Image();
		Image expected2 = new Image(testDrinkId, new byte[] {});
		Image expected3 = new Image(testDrinkId, new byte[] {});
		expectedImages.add(expected1);
		expectedImages.add(expected2);
		expectedImages.add(expected3);
		when(imageRepository.findAll()).thenReturn(expectedImages);
		
		List<Image> returnedImages = imageService.getAllImages();
		
		verify(imageRepository, times(1)).findAll();
		assertEquals(expectedImages, returnedImages);
		
	}
	
	
	@Test
	public void getImageByIdCallsFindById() {

		Long imageId = 3L;
		
		imageService.getImageById(imageId);
		
		verify(imageRepository, times(1)).findById(imageId);
		
	}

}
