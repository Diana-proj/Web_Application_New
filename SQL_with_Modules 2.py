import requests
from bs4 import BeautifulSoup
import sqlite3


# Send a get request to the scss webpage
url = 'https://teaching.scss.tcd.ie/general-information/scss-modules/'
html = requests.get(url)
html

# Parse the page content
soup = BeautifulSoup(html.text, 'html.parser')

#print(soup.prettify())

#print (soup.a)

# Create empty arrays 
module_code = []
module_name = []
module_link = []

# Find all module titles 
modules = soup.find_all("h4", class_="wp-show-posts-entry-title")


# Creating SQL
conn = sqlite3.connect('modules.db')
cursor = conn.cursor()
cursor.execute("DROP TABLE IF EXISTS Modules")
file = 'tcdmodules.sql'
cursor.execute('''
    CREATE TABLE IF NOT EXISTS Modules (
        module_code TEXT,
        module_name TEXT,
        usefulness REAL DEFAULT 0,
        easiness REAL DEFAULT 0,
        overall_rating REAL DEFAULT 0
            )
                   ''')

# Extract title, code + links
count = 0
for module in modules:
    module_text = module.find("a").get_text(strip=True)
    print(f"{count}. {module_text}")   # 137 modules
    count += 1

    if ' – ' in module_text:
        code,name = module_text.split (' – ', 1)
    else: 
        code,name = module_text.split (' ', 1)
    module_code.append (code)
    module_name.append (name)

    module_url = module.find("a").get("href")
    module_link.append (module_url)
    
    # Insert the data into the SQL table
    cursor.execute('''
        INSERT INTO Modules (module_code, module_name, usefulness, easiness, overall_rating)
        VALUES (?, ?, 0, 0, 0)
        ''', (code, name))


# Commit changes and close connection
conn.commit()
cursor.close()
conn.close()

print ("Done")

"""
# Show the table

conn = sqlite3.connect('modules.db')
cursor = conn.cursor()
cursor.execute("SELECT * FROM Modules")
rows = cursor.fetchall()

# Print out the rows
count = 0
for row in rows:
    print(f'{count}.{row}')
    count+=1

# Close the connection
conn.close()"""