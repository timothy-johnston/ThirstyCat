from django.shortcuts import render
from django.http import HttpResponse
from restAPI.models import Drinks
from django.utils import timezone
from rest_framework.parsers import FileUploadParser
from rest_framework.response import Response
from rest_framework.views import APIView
import datetime
from django.views.decorators.csrf import csrf_exempt
from django.utils.decorators import method_decorator

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

@method_decorator(csrf_exempt, name='dispatch')
class FileUploadView(APIView):
    parser_classes = (FileUploadParser,)

    #@csrf_exempt
    def put(self, request, filename, format = None):
        print ("about to do request.data-----------------------------")
        #file_obj = request.data['file']
        filepath = request.FILES.get('filename')
        print(filepath)
        print(request.body)
        print("-----------------")
        up_file = request.FILES['file']
        destination = open('/Users/Username/' + up_file.name, 'wb+')
        for chunk in up_file.chunks():
            destination.write(chunk)
            destination.close()
        print ("----------got past request.data-----------------------")

        #DO STUFF WITH FILE HERE


        return Response(status = 204)
 
