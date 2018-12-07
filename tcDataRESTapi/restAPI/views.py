from django.shortcuts import render
from django.http import HttpResponse
from restAPI.models import Drinks
from django.utils import timezone
import datetime

# Create your views here.

def getDrinksToday(request):
    return HttpResponse(Drinks.objects.latest('id').drinksToday)

def getTimeOfLastDrink(request):
    return HttpResponse(Drinks.objects.latest('id').timeEnd)

def getPhotoOfLastDrink(request):
    return HttpResponse(Drinks.objects.latest('id').photoPath)

def addDrink(request, startTime, endTime, numDrinks):
    newdrink = Drinks(timeStart = startTime, timeEnd = endTime, photoPath = '/photos/img+startTime', drinksToday = numDrinks)
    newdrink.save()
    return HttpResponse('New entry added: ' + newdrink)

def addDrinkBasic(request, numDrinks):
    #Use dummy data for the dates and photo path for the time being
    #TODO: Remove this method, fix date formatting in addDrink
    newdrink = Drinks(timeStart = timezone.now(), timeEnd = timezone.now(), photoPath = '/photos/img+startTime', drinksToday = numDrinks)
    newdrink.save()
    return HttpResponse('New entry added: ' + str(newdrink.drinksToday))
 
