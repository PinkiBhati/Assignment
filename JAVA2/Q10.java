class Customer {

        private String Name;
        private double PhoneNo;
        private int TokenNo;
        Customer(String name,double phone)
        {
            this.Name =name;
            this.PhoneNo =phone;
        }
        public String getName() {
            return Name;
        }

        public void setName(String name) {
            this.Name = name;
        }

        public double getPhoneNo() {
            return PhoneNo;
        }

        public void setPhoneNo(double phoneNo) {
            this.PhoneNo = phoneNo;
        }

        public int getTokenNo() {
            return TokenNo;
        }

        public void setTokenNo(int tokenNo) {
            this.TokenNo = tokenNo;
        }
        public void PlaceOrder(String Name, double phoneNo)
        {

            //we will place order on the basis of name and phone number
        }
        public void checkOrderStatus(int tokeno)
        {

            // we will checkorderstatus on the basis of tokenno given to the customer

        }
        public void collectOrder(int tokeno)
        {
            // customer can collect the coffee after entering tokenno
        }
        public void payment()
        {
            //payment done through this function
        }



    }

class Cashier {


        String nameOfCashier;
        int CashierId;


        public String getNameOfCashier() {
            return nameOfCashier;
        }

        public void setNameOfCashier(String nameOfCashier) {
            this.nameOfCashier = nameOfCashier;
        }

        public int getCashierId() {
            return CashierId;
        }

        public void setCashierId(int cashierId) {
            CashierId = cashierId;
        }

        public void takeOrder(String customerName)
        {
            //cashier will take the order
        }
        public void giveTokenNo(String customerName)
        {
            //this will be called when customer places an order so cashier will provide him with a tokenno
        }
        public void receivePayment(int tokenno)
        {
            //
        }
        public void addItToPendingQueue(int tokeno)
        {

        }



    }

class Barista {

    public void getOrderFromPendingQueue()
        {

        }
        public void prepareOrder()
        {

        }
        public void insertOrderIntoCompletedOrderQueue()
        {

        }

        public void PushNotification()
        {

        }
    }


public class Q10 {
    public static void main(String[] args) {

                Cashier cashier = new Cashier();
                Barista barista = new Barista();
                Customer customer = new Customer("PINKI",786543290);
                customer.PlaceOrder(customer.getName(),customer.getPhoneNo());
                cashier.takeOrder(customer.getName());
                cashier.giveTokenNo(customer.getName());
                cashier.receivePayment(customer.getTokenNo());
                cashier.addItToPendingQueue(customer.getTokenNo());
                customer.checkOrderStatus(customer.getTokenNo());
                barista.getOrderFromPendingQueue();
                barista.prepareOrder();
                barista.PushNotification();
                barista.insertOrderIntoCompletedOrderQueue();
                customer.collectOrder(customer.getTokenNo());
                customer.payment();

            }
        }




