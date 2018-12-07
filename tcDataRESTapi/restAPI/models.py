from django.db import models
from django.utils import timezone
import datetime

# Create your models here.

class Drinks(models.Model):
    timeStart = models.DateTimeField()
    timeEnd = models.DateTimeField()
    photoPath = models.CharField(max_length = 256)
    drinksToday = models.IntegerField(default = 0)
    objects = models.Manager()
    def __str__(self):
        return self.photoPath
    def recentlyGotDrink(self):
        return self.timeStart >= timezone.now() - datetime.timedelta(days = 1)
    