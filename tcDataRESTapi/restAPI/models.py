from django.db import models
from django.utils import timezone
import datetime

# Create your models here.

class Drinks(models.Model):
    time_start = models.DateTimeField()
    time_end = models.DateTimeField()
    photo_path = models.CharField(max_length = 256)
    drinks_today = models.IntegerField(default = 0)
    def __str__(self):
        return self.photo_path
    def recentlyGotDrink(self):
        return self.time_start >= timezone.now() - datetime.timedelta(days = 1)
    
