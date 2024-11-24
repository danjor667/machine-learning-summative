from django.contrib.auth import authenticate
from rest_framework import generics, mixins, status
from rest_framework.authtoken.models import Token
from rest_framework.response import Response
from rest_framework.views import APIView

from .models import User
from .serializers import UserSerializer


class UserCreate(mixins.CreateModelMixin, generics.GenericAPIView):
    serializer_class = UserSerializer
    queryset = User.objects.all()

    def post(self, request, *args, **kwargs):
        serializer = UserSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            user = serializer.save()
            return Response({"email": user.email}, status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)

register_view = UserCreate.as_view()



class UserLogin(APIView):
    def post(self, request, **kwargs):
        data = request.data
        email = data.get("email")
        password = data.get("password")
        user = authenticate(username=email, password=password)
        if user:
            token, _ = Token.objects.get_or_create(user=user)
            return Response({'email': user.email, 'token': token.key}, status=status.HTTP_200_OK)
        return Response({"message": "wrong credentials"}, status=status.HTTP_400_BAD_REQUEST)

login_view = UserLogin.as_view()







