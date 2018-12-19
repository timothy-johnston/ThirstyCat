from django.urls import path
from django.conf.urls import url, include
from . import views
from rest_framework import routers
from rest_framework.parsers import FileUploadParser
from rest_framework.response import Response
from rest_framework.views import APIView

urlpatterns = [
    path('drinks/drinkstoday', views.getDrinksToday, name= 'drinkstoday'),
    #path('drinks/timeoflastdrink', views.getTimeOfLastDrink, name= 'timeoflastdrink'),
    #path('drinks/photooflastdrink', views.getPhotoOfLastDrink, name= 'photooflastdrink'),
    path('drinks/add/<str:startTime>+<str:endTime>+<int:numDrinks>', views.addDrink),
    path('drinks/addBasic/<int:numDrinks>', views.addDrinkBasic, name = 'addDrinkBasic'),
    path('drinks/imageupload/<str:filename>', views.FileUploadView),
    path("health/ping", views.healthCheckPing),
    path('health/status', views.healthCheckStatus),
]