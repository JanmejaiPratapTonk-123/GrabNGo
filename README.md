# GrabNGo

GrabNGo is a self-service food ordering kiosk application developed using Java Swing. The application allows users to browse menu items, add products to a cart, make payments, and view previous orders through a simple graphical interface.

## Features

* Browse food and drink items
* Add and remove items from the cart
* Automatic calculation of order totals
* Cash and UPI payment options
* Order confirmation with token generation
* Order history management
* Data stored locally using a text file
* User-friendly Java Swing interface

## Getting Started

### Prerequisites

* Java JDK 8 or above

### Running the Project

Clone the repository:

```bash
git clone https://github.com/janmejaiprataptonk-123/grabngo.git
```

### Windows

Run:

```bash
run.bat
```

### Linux / macOS

```bash
cd grabngo
mkdir bin
find . -name "*.java" > sources.txt
javac -d bin @sources.txt
java -cp bin frontend.App
```

The application automatically creates an `orders.txt` file to store order records.

## Project Structure

```text
grabngo/
├── backend/
│   ├── controllers/
│   ├── services/
│   ├── models/
│   └── dao/
│
└── frontend/
    ├── App.java
    ├── MainFrame.java
    ├── panels/
    ├── dialogs/
    └── ui/
```

## Technologies Used

* Java
* Java Swing
* Object-Oriented Programming
* MVC-inspired Architecture
* File Handling

## How the Application Works

1. Launch the application.
2. Browse available menu items.
3. Add items to the cart.
4. Review the order summary.
5. Select a payment method.
6. Confirm the order.
7. View order history if required.

## Future Improvements

* Database integration
* User authentication
* Admin panel
* Inventory management
* Online payment gateway support

## Authors

Developed as a Java academic project.

* Shivika Sharma
* Janmejai Pratap Tonk

## License

This project is intended for educational purposes only.
