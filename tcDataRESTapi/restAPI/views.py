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
from braces.views import CsrfExemptMixin
from rest_framework.authentication import SessionAuthentication, BasicAuthentication
import base64

lastPingTime = timezone.now() - timezone.now()

# Create views here.

def getDrinksToday(request):
    return HttpResponse(Drinks.objects.latest('id').drinksToday)

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

def healthCheckPing(request):
    lastPingTime = timezone.now()                               #Not working currently, fix scope of lastPingTime
    return HttpResponse(200)

def healthCheckStatus(request):
    #The piControl script should hit this endpoint once every 5 min
    #If it hasn't, the program is down                     
    if (timezone.now().minute - lastPingTime.minute > 5):     #Not working currently, fix scope of lastPingTime      
        status = "down"
    else:
        status = "up"

    return HttpResponse('Status: ' + status + '. Last update: ' + lastPingTime.hour + ':' + lastPingTime.minute + ':' + lastPingTime.second)


#Below code is toying with making endpoints for uplaoding/retrieving images
#Should create feature branch and remove this from master

from rest_framework.authentication import SessionAuthentication, BasicAuthentication 

class CsrfExemptSessionAuthentication(SessionAuthentication):

    def enforce_csrf(self, request):
        return



#@method_decorator(csrf_exempt, name='dispatch')
class FileUploadView(CsrfExemptMixin, APIView):
    authentication_classes = (CsrfExemptSessionAuthentication, BasicAuthentication)
    parser_classes = (FileUploadParser,)

    #@csrf_exempt
    def post(self, request, filename, format = 'png'):
        if request.method == 'POST':
            print("HELLO!!!!!!!!!!!!!!!!!")
        
        
        print ("------------about to do request.data-----------------------------")
        #file_obj = request.data['file']
        filepath = request.FILES.get('file')
        print(filename)
        print(filepath)
        print(request.FILES)
        print('OK')
        print(request.POST.keys())
        print(request.FILES.keys())
        # print(request.body)
        # print(request.data)
        print("-----------finished printing------------")
        up_file = request.FILES['file']
        destination = open(up_file.name, 'wb+')
        for chunk in up_file.chunks():
            destination.write(chunk)
            destination.close()
        print ("----------got past request.data-----------------------")

        #Look at this next
        #https://stackoverflow.com/questions/16905724/post-an-inmemoryuploadedfile-from-a-file-input-field-to-another-external-server
        #https://stackoverflow.com/questions/505868/django-how-do-you-turn-an-inmemoryuploadedfile-into-an-imagefields-fieldfile

        #DO STUFF WITH FILE HERE
        # imageStr
        # f = open('file', 'rb')
        # binaryText = f.read()
        # uploadedPic = open("uploadedPic.png", "wb")
        # uploadedPic.write(binaryText.decode('base64'))
        # uploadedPic.close()
        # with open('file') as f:
        #     lines = f.readlines()
        # for line in lines[:-1]:
        #     print(line)


        return Response(status = 204)
 
