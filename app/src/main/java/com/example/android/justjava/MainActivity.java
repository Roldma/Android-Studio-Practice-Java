package com.example.android.justjava;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;
    public void increment(View view) {
        quantity += 1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        quantity -= 1;
        displayQuantity(quantity);
    }
    public void submitOrder(View view) {
        int priceTotal;
        priceTotal = calculatePrice();
        boolean firstTopping;
        firstTopping = firstToppingCheck();
        String orderSummary = createOrderSummary(priceTotal,firstTopping);
        displayMessage(orderSummary);
    }
    private String createOrderSummary(int price ,boolean firstTopping) {
        int itemPrice = price * quantity;
        String orderSummaryToppings;
        orderSummaryToppings = "\n" + "Add Whipped Cream? "+ firstTopping + "\n";
        String orderSummaryMessage;
        orderSummaryMessage = "Name: Kunal Johnson" + "\nQuantity: " + quantity + orderSummaryToppings +
                "\nTotal: $" + itemPrice + "\nThank you.";
        return orderSummaryMessage;
    }
    private int calculatePrice() {
        int priceTotal = 5 * quantity;
        return priceTotal;
    }
    private boolean firstToppingCheck(){
        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_button);
        boolean yesWhipped = whippedCreamCheck.isChecked();
        return yesWhipped;
    }
    private void displayQuantity(int numba) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numba);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }
}