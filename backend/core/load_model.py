import pickle
from pathlib import Path
import os

BASE = Path(__file__).resolve().parent.parent.parent

with open(os.path.join(BASE, "ML/new_model.pkl"), "rb") as file:
    MODEL = pickle.load(file)

with open(os.path.join(BASE, "ML/scaler1.pkl"), "rb") as file2:
    SCALER = pickle.load(file2)