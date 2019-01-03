from django import forms
from .import models

class UploadFileForm(forms.Form):
    title = forms.CharField(max_length=500)
    file = forms.ImageField()

class UploadImageForm(forms.ModelForm):
    image = forms.FileField(
        label = 'Select a file',
        help_text = 'max. 42 megabytes'
    )
    # class Meta:
    #     model = UploadedImage