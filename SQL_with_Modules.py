import requests
from bs4 import BeautifulSoup
import sqlite3

# Send a get request to the SCSS webpage
url = 'https://teaching.scss.tcd.ie/general-information/scss-modules/'
html = requests.get(url)

# Parse the page content
soup = BeautifulSoup(html.text, 'html.parser')

# Find all module titles 
modules = soup.find_all("h4", class_="wp-show-posts-entry-title")

# Creating the database and tables
conn = sqlite3.connect('modules.db')
cursor = conn.cursor()

# Drop existing tables if any (for clean execution during testing)
cursor.execute("DROP TABLE IF EXISTS Modules")
cursor.execute("DROP TABLE IF EXISTS Reviews")

# Create the Modules table
cursor.execute('''
    CREATE TABLE IF NOT EXISTS Modules (
        Module_Code TEXT PRIMARY KEY,
        Module_Name TEXT,
        Module_Content TEXT,
        Module_Coordinator TEXT,
        Workload REAL DEFAULT 0,
        Difficulty REAL DEFAULT 0,
        Usefulness REAL DEFAULT 0,
        Overall_Rating REAL DEFAULT 0
    )
''')

# Create the Reviews table
cursor.execute('''
    CREATE TABLE IF NOT EXISTS Reviews (
        Review_Id INTEGER PRIMARY KEY AUTOINCREMENT,
        Module_Code TEXT,
        Module_Name TEXT,
        Module_Content TEXT,
        Review_Text TEXT,
        Workload REAL,
        Difficulty REAL,
        Usefulness REAL,
        Overall_Rating REAL,
        FOREIGN KEY (Module_Code) REFERENCES Modules(Module_Code)
    )
''')

# Extract and insert module details into the Modules table
for module in modules:
    module_text = module.find("a").get_text(strip=True)
    if ' – ' in module_text:
        code, name = module_text.split(' – ', 1)
    else:
        code, name = module_text.split(' ', 1)

    module_url = module.find("a").get("href")

    # Accessing each module's page and parsing
    response = requests.get(module_url)
    module_soup = BeautifulSoup(response.text, 'html.parser')

    # Extract module content
    module_content = module_soup.find('h2', class_='wp-block-heading', string="Module Content")
    module_description = []
    if module_content:
        next_element = module_content.find_next_sibling()
        while next_element:
            if next_element.name in ['p', 'ul', 'ol']:
                module_description.append(next_element.text.strip())
            elif next_element.name == 'figure':
                for cell in next_element.find_all('td'):
                    module_description.append(cell.get_text())
            elif next_element.name in ['h2', 'h3', 'h4', 'h5', 'h6']:
                break
            next_element = next_element.find_next_sibling()
    else:
        list_items = module_soup.find_all('li')
        for li in list_items:
            module_description.append(li.text.strip())
    full_description = " ".join(module_description)

    # Extract the module coordinator
    rows = module_soup.find_all("tr")
    coordinator = None
    for row in rows:
        cell = row.find("td")
        if cell and "Module Coordinator/s" in cell.get_text():
            coordinator_cell = cell.find_next_sibling("td")
            coordinator = coordinator_cell.get_text(strip=True)
            break

    # Insert the data into the Modules table
    cursor.execute('''
        INSERT INTO Modules (Module_Code, Module_Name, Module_Content, Module_Coordinator, Workload, Difficulty, Usefulness, Overall_Rating)
        VALUES (?, ?, ?, ?, 0, 0, 0, 0)
    ''', (code, name, full_description, coordinator))

# Dummy reviews 
reviews = [
    {
        "module_code": "CSU11021",
        "workload": 4,
        "difficulty": 4,
        "usefulness": 5,
        "overall_rating": 4.3
    },
    { "module_code": "CE7C05",
        "workload": 4,
        "difficulty": 4,
        "usefulness": 5,
        "overall_rating": 4.3},
    {
        "module_code": "CSU11011",
        "workload": 5,
        "difficulty": 4,
        "usefulness": 5,
        "overall_rating": 4.7
    },
    {
        "module_code": "CEP55E03",
        "workload": 5,
        "difficulty": 4,
        "usefulness": 5,
        "overall_rating": 4.7
    },
    
    {
        "module_code": "CSU11001",
        "workload": 4,
        "difficulty": 5,
        "usefulness": 4,
        "overall_rating": 4.3
    },
    {
        "module_code": "CS7CS4",
        "workload": 5,
        "difficulty": 5,
        "usefulness": 5,
        "overall_rating": 5.0
    },
    {
        "module_code": "CS7CS6",
        "workload": 3,
        "difficulty": 3,
        "usefulness": 4,
        "overall_rating": 3.7
    }
]

# Ensure modules exist before updating
existing_modules = cursor.execute('SELECT Module_Code FROM Modules').fetchall()
existing_codes = {row[0] for row in existing_modules}

for review in reviews:
    if review["module_code"] in existing_codes:
        cursor.execute('''
            UPDATE Modules
            SET Workload = ?, Difficulty = ?, Usefulness = ?, Overall_Rating = ?
            WHERE Module_Code = ?
        ''', (
            review["workload"],
            review["difficulty"],
            review["usefulness"],
            review["overall_rating"],
            review["module_code"]
        ))


# Commit changes and close the connection
conn.commit()
conn.close()

print("Done")


"""# Show the table

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