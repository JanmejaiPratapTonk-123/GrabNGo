# GrabNGo 🍔

A modern, self-service food ordering kiosk application built with Java Swing. GrabNGo provides an intuitive interface for customers to browse menus, manage shopping carts, and complete payments seamlessly. The application features persistent order storage, comprehensive order history tracking, and a polished user experience with custom-themed components.

---

## ✨ Features

- **Intuitive User Interface** — Clean and responsive design built with Java Swing and custom-themed components for a polished, modern look
- **Dynamic Menu System** — Browse food and drink items with category filtering (Snacks, Drinks, and more)
- **Interactive Shopping Cart** — Real-time quantity updates and order total calculations with easy item management
- **Flexible Payment Options** — Support for both Cash and simulated UPI payments, including a mock QR code for UPI transactions
- **Order Confirmation** — Unique token numbers generated for each successfully placed order
- **Persistent Order History** — All completed orders saved to `orders.txt` with a comprehensive history viewer featuring sorting and search capabilities
- **Engaging Splash Screen** — Professional welcome screen to start the ordering experience

---

## 🚀 Getting Started

### Prerequisites

- **Java Development Kit (JDK) 8 or higher** — Ensure Java is installed and properly configured on your system

### Installation & Running

#### Windows

1. Clone the repository:
   ```bash
   git clone https://github.com/janmejaiprataptonk-123/grabngo.git
   ```

2. Navigate to the repository directory and run `run.bat`

The script will automatically compile all Java source files into the `bin` directory and launch the application.

#### macOS / Linux

1. Clone the repository and navigate into the project directory:
   ```bash
   git clone https://github.com/janmejaiprataptonk-123/grabngo.git
   cd grabngo
   ```

2. Create a directory for compiled classes:
   ```bash
   mkdir bin
   ```

3. Compile all Java source files:
   ```bash
   find . -name "*.java" > sources.txt
   javac -d bin @sources.txt
   ```

4. Run the application:
   ```bash
   java -cp bin frontend.App
   ```

**Note:** The application will automatically create an `orders.txt` file in the root directory to store order history on first run.

---

## 📁 Project Structure

The project follows a clean **MVC-inspired architecture** with clear separation between frontend (UI) and backend (business logic):

```
grabngo/
├── backend/                          # Business logic & data handling
│   ├── controllers/
│   │   └── KioskController.java     # Central facade connecting frontend to services
│   ├── services/
│   │   ├── CartService.java         # Shopping cart operations
│   │   ├── MenuService.java         # Menu data management
│   │   ├── OrderService.java        # Order creation & processing
│   │   └── PaymentService.java      # Payment processing logic
│   ├── models/
│   │   ├── MenuItem.java            # Menu item data structure
│   │   ├── CartItem.java            # Shopping cart item structure
│   │   └── Order.java               # Order data structure
│   └── dao/
│       └── OrderFileDAO.java        # Data persistence (orders.txt)
│
└── frontend/                         # User interface components
    ├── App.java                     # Main application entry point
    ├── MainFrame.java               # Primary window with CardLayout
    ├── panels/
    │   ├── SplashPanel.java         # Welcome/splash screen
    │   ├── KioskPanel.java          # Main ordering screen
    │   ├── MenuPanel.java           # Menu display
    │   └── CartPanel.java           # Shopping cart view
    ├── dialogs/
    │   ├── PaymentDialog.java       # Payment method selection
    │   ├── ConfirmationDialog.java  # Order confirmation
    │   └── OrderHistoryDialog.java  # Order history viewer
    └── ui/
        ├── RoundedButton.java       # Custom rounded button component
        ├── FoodIconPainter.java     # Food item icon rendering
        └── AppTheme.java            # Application color scheme & styling
```

### Architecture Highlights

- **KioskController**: Serves as the central facade, coordinating all backend services
- **Service Layer**: Modular services handle specific business logic (cart, menu, orders, payments)
- **DAO Pattern**: `OrderFileDAO` abstracts data persistence, keeping file I/O separate from business logic
- **Swing Components**: Custom UI components provide a cohesive, branded user experience

---

## 💻 Technology Stack

- **Language**: Java (JDK 8+)
- **GUI Framework**: Java Swing
- **Architecture Pattern**: MVC-inspired with Service & DAO layers
- **Data Storage**: File-based (orders.txt)

---

## 📝 Usage

1. **Start the Application** — Launch GrabNGo to see the splash screen
2. **Browse Menu** — Navigate through food and drink categories
3. **Manage Cart** — Add items to cart and adjust quantities
4. **Checkout** — Choose payment method (Cash or UPI)
5. **Order Confirmation** — Receive unique token number for your order
6. **View History** — Access previous orders through the order history dialog

---

## 🛠️ Development

### Building from Source

```bash
# Compile all Java files
javac -d bin $(find . -name "*.java")

# Run the application
java -cp bin frontend.App
```

### Key Classes

- `frontend.App` — Application entry point
- `frontend.MainFrame` — Main window controller
- `backend.controllers.KioskController` — Business logic orchestrator
- `backend.dao.OrderFileDAO` — Persistent storage handler

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repository, make improvements, and submit pull requests.

---

## 📧 Support

For issues, feature requests, or questions, please open an issue on the [GitHub repository](https://github.com/janmejaiprataptonk-123/grabngo).

---

**Happy Ordering! 🎉**
