from django.shortcuts import render
from django.http import HttpResponse
from restAPI.models import Drinks
from restAPI.models import Images
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
import os
from django.core.files.storage import default_storage
from django.core.files.base import ContentFile
from django.conf import settings
from django.http import HttpResponse, HttpResponseBadRequest, HttpResponseNotAllowed
from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.shortcuts import render_to_response
from django.template import RequestContext
from .forms import UploadFileForm, UploadImageForm
from .models import UploadedImage


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
        # if request.method == 'POST':
        #     print("HELLO!!!!!!!!!!!!!!!!!")
        #     form = UploadFileForm(request.POST, request.FILES)
        #     if form.is_valid():
        #         print("FORM WAS VALID!!!!!!!!!!!!!!!!!!!!!!!1")
        #         handle_uploaded_file(request.FILES['file'])
        #         return HttpResponseRedirect('/success/url/')
        # else:
        #     form = UploadFileForm()
        # return render(request, 'upload.html', {'form':form})
        
        if request.method == 'POST':
            print('About to instantiate form')
            form = UploadImageForm(request.POST or None)
            print(form.errors)
            #if form.is_valid():
            form.save()
            newimg = UploadedImage(imgfile = request.FILES['file'])
            newimg.save()

            return Response('You did it you absolute unit')

        else:
            return Response('yeah that didnt work')

        
        #For debugging. Currently won't get here:
        print ("------------about to do request.data-----------------------------")
        #file_obj = request.data['file']
        filepath = request.FILES.get('file')
        print(filename)
        print(filepath)
        print(request.FILES)
        print('OK')
        print(request.POST.keys())
        print(request.FILES.keys())
        print("-----------finished printing------------")
        up_file = request.FILES['file']
        destination = open(up_file.name, 'wb+')
        for chunk in up_file.chunks():
            destination.write(chunk)
            destination.close()
        # img = Images()
        # img.image.save(up_file.name, File(open(up_file.name, 'r')))
        # img.save()

        print ("----------got past request.data-----------------------")

        #Look at this next
        #https://stackoverflow.com/questions/16905724/post-an-inmemoryuploadedfile-from-a-file-input-field-to-another-external-server
        #https://stackoverflow.com/questions/505868/django-how-do-you-turn-an-inmemoryuploadedfile-into-an-imagefields-fieldfile

        #DO STUFF WITH FILE HERE

        data = request.FILES['file'].file
        path = default_storage.save('savedimage.png', ContentFile(data.read()))
        tmp_file = os.path.join(settings.MEDIA_ROOT, path)

        return Response(status = 204)
 
def handle_uploaded_file(f):
    with open('testfilename.png', 'wb+') as destination:
        for chunk in f.chunks():
            destination.write(chunk)