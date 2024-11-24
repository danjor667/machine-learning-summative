from django.http import HttpResponse
from rest_framework import status, authentication
from rest_framework.renderers import JSONRenderer
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.permissions import IsAuthenticated

from .load_model import BASE, MODEL, SCALER
import pandas as pd
import numpy as np



def home(request):
    return HttpResponse("Diabetes predictions")

class  DiabetesPredictor(APIView):
    renderer_classes = [JSONRenderer]
    permission_classes = [IsAuthenticated]
    authentication_classes = [authentication.TokenAuthentication, authentication.SessionAuthentication]
    def post(self, request, *args, **kwargs):
        try:

            data = request.data
            features_names = ["Pregnancies",
                              "Glucose",
                              "BloodPressure",
                              "SkinThickness",
                              "Insulin",
                              "BMI",
                              "DiabetesPedigreeFunction",
                              "Age"
                              ]

            X = [[data["Pregnancies"], data["Glucose"], data["BloodPressure"], data["SkinThickness"], data["Insulin"], data["BMI"], data["DiabetesPedigreeFunction"], data["Age"]]]
            X = np.array(X)
            X = pd.DataFrame(X, columns=features_names)

            scaled_X = SCALER.transform(X)
            scaled_X = pd.DataFrame(scaled_X, columns=features_names)
            prediction = MODEL.predict(scaled_X)
            probability = MODEL.predict_proba(scaled_X)
            return Response({"prediction": prediction, "probability": probability[0]}, status=status.HTTP_200_OK)
        except Exception as e:
            return Response({"error": f"{e}"}, status=status.HTTP_400_BAD_REQUEST)


predict_view = DiabetesPredictor.as_view()