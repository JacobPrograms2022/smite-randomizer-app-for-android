# scraping god names and images using https://smitegame.com/gods/

from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver import Keys
import time
import requests
import json

# setting up the web driver
driver = webdriver.Edge()
driver.get("https://smitegame.com/gods/")
time.sleep(2)

# finding page HTML 
html = driver.page_source

# HTML parser
soup = BeautifulSoup(html, "html.parser")

# returns all god HTML details
all_gods = soup.find_all("a", "single__god")
new_god = soup.find_all("a", "single__god--new")
combined_gods = all_gods+new_god
# JSON generation
def god_names(all_gods, new_gods):
    all_god_names_list = []
    for i in range(len(all_gods)):
        god_name = all_gods[i].find("div", "details__name").get_text().strip()
        all_god_names_list.append(god_name)

    for i in range(len(new_gods)):
        god_title = new_gods[i].find("div", "details__title").get_text().strip()
        god_name = god_title.split(" -")
        all_god_names_list.append(god_name[0])
    return all_god_names_list

god_names_list = god_names(all_gods, new_god)

class SmiteGodData:
    def __init__(self, name):
        self.name = name
    
        god_data = []
        for i in combined_gods:
            if i.find("div", "details__name").get_text().strip() == self.name or self.name in i.find("div", "details__title").get_text().strip():
                god_data.append(i)
        self.god_data = god_data[0]

    def god_image(self):
        # image of gods need resizing manually (Or use AI :P)
        god_image = str(self.god_data.find("div")["style"]).split(" url")
        god_image_repaired = god_image[1].strip('("");')
        img_data = requests.get(god_image_repaired).content
        file_path = r"C:\Users\jacob\OneDrive\Pictures\test_folder"
        with open(file_path+"\\"+self.name.lower().replace(" ", "_")+"_image.png", "wb") as handler:
            handler.write(img_data)
        god_image_saved = self.name.lower().replace(" ", "_")+"_resized"
        return god_image_saved

    def god_class(self):
        god_class = self.god_data.find("div", "class__distance").get_text().strip()
        return god_class # note: Image must be resized to 310x180px
    
    def god_auto_range(self):
        god_autos = self.god_data.find_all("div", "class__type")
        return god_autos[0].get_text().strip()
    
    def god_damage_type(self):
        god_damage = self.god_data.find_all("div", "class__type")
        return god_damage[1].get_text().strip()


def update_file():
    master_list = []
    for name in god_names_list:
        god_dict = {}
        instance = SmiteGodData(name)
        god_dict["name"] = name
        god_dict["img"] = instance.god_image() # needs resizing
        god_dict["class"] = instance.god_class()        
        god_dict["attack type"] = instance.god_auto_range()
        god_dict["power type"] = instance.god_damage_type()
        master_list.append(god_dict)

    return master_list

master_data_list = update_file()
with open(r"C:\Users\jacob\AndroidStudioProjects\smite_randomizer_3\app\src\main\assets\smite_gods_2.json", "w") as json_file:
    json.dump(master_data_list, json_file, indent='\t')