from django.urls import path
from . import views

urlpatterns = [
    path('drinks/drinkstoday', views.getDrinksToday, name= 'drinkstoday'),
    path('drinks/timeoflastdrink', views.getTimeOfLastDrink, name= 'timeoflastdrink'),
    path('drinks/photooflastdrink', views.getPhotoOfLastDrink,name= 'photooflastdrink'),
    path('drinks/add/<str:startTime>+<str:endTime>+<int:numDrinks>', views.addDrink),
    path('drinks/addBasic/<int:numDrinks>', views.addDrinkBasic, name = 'addDrinkBasic')
]