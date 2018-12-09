from rest_framework import serializers, viewsets
from . import models
from .models import Images


class ImagesSerializer(serializers.ModelSerializer):
    class meta:
        model = Images
        fields = ('pk', 'image', )      #serializes the primary key and image field