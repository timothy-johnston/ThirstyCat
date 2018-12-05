from tastypie.resources import ModelResource
from restAPI.models import Drinks

class DrinksResource(ModelResource):
    class Meta:
        queryset = Drinks.objects.all()
        resource_name = 'drinks'