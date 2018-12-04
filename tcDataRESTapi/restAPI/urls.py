from django.urls import path
from . import views

urlpatterns = [
    path('drinkstoday', views.getDrinksToday, name='drinkstoday'),
    path('timeoflastdrink', views.getTimeOfLastDrink, name='timeoflastdrink'),
    path('photooflastdrink', views.getPhotoOfLastDrink,name='photooflastdrink'),
]