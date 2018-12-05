from django.urls import path
from . import views

urlpatterns = [
    path('/drinks/drinkstoday', views.getDrinksToday, name= 'drinkstoday'),
    path('/drinks/timeoflastdrink', views.getTimeOfLastDrink, name= 'timeoflastdrink'),
    path('/drinks/photooflastdrink', views.getPhotoOfLastDrink,name= 'photooflastdrink'),
    path('/drinks/adddrink/<str:startTime>+<str:endTime>+<str:photoPath>+<int:drinksToday>', views.addDrink),
]