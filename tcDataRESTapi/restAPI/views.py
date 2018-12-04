from django.shortcuts import render
from django.http import HttpResponse

# Create your views here.

def getDrinksToday(request):
    return HttpResponse("This will get # of drinks today from db (Also, Hello, world!)")

def getTimeOfLastDrink(request):
    return HttpResponse("This will get time of last drink from db")

def getPhotoOfLastDrink(request):
    return HttpResponse("Gotta figure out how to GET media files...")
