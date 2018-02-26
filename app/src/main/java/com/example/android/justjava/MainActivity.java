package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
    * Methods used to increment and decrement the quantity with the + and - buttons.
    * Also the method which creates a toast to notify the user if there is a error in quantity.
    */
    int quantity = 0;
    public void increment(View view) {
        if(quantity <= 99) {
            quantity += 1;
            displayQuantity(quantity);
        } else {
            quantityErrorMessage("You cannot order more than 100 coffees.");
        }
        // quantity += 1;
        // displayQuantity(quantity);
    }
    public void decrement(View view) {
        if(quantity >= 1) {
            quantity -= 1;
            displayQuantity(quantity);
        } else {
            quantityErrorMessage("You cannot order negative coffee");
        }
        // quantity -= 1;
        // displayQuantity(quantity);
    }
    public void quantityErrorMessage(CharSequence errorMessage) {
        Context context = getApplicationContext();
        int duration =  Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,errorMessage,duration);
        toast.show();
    }

    /*
    * Method connected to the submit order button.
    * Includes the price calculated for the order
     *Initializes variable used to calculate the price and calls the method to calculate the price
     * Initializes variable used to create the string for the order summary and calls the method to create the order summary
    */
    public void submitOrder(View view) {
        int priceTotal = calculatePrice(firstToppingCheck(),secondToppingCheck());
        // boolean firstTopping = firstToppingCheck();
        // boolean secondTopping = secondToppingCheck();
        String orderSummary = createOrderSummary(priceTotal,firstToppingCheck(),secondToppingCheck());
        // displayMessage(orderSummary);
    /*
    * 'Intent' to compose the order summary into an email.
    */
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SENDTO);
        emailIntent.setType("text/plain");
        emailIntent.setData(Uri.parse("mailto:")); //Should only be handled by email application
        emailIntent.putExtra(Intent.EXTRA_TEXT,orderSummary); // contains text for the body of the email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Your order at CoffeeBeanz"); //contains text for the subject line of email
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
            }    // if statement used to check if there is an application on the phone that can handle the intent.
                 // if there is no application, then the startActivity() will be skipped and the app will not crash.
    }

    /*
    * Creates the string for the order summary
     */
    private String createOrderSummary(int price ,boolean firstTopping,boolean secondTopping) {
        int itemPrice = price;
        String toppingOneMessage = "\n" + getString(R.string.summary_topping_one) + toppingCheckConversion(firstTopping);
        String toppingTwoMessage = "\n" + getString(R.string.summary_topping_two) + toppingCheckConversion(secondTopping);
        String personsName = name_space();
        String orderSummaryMessage;
        orderSummaryMessage = "Name: " + personsName + "\nQuantity: " + quantity + toppingOneMessage +
                toppingTwoMessage + "\nTotal: $" + itemPrice + "\nThank you.";
        String localizedOrderSummaryMessage;
        localizedOrderSummaryMessage =
                getString(R.string.summary_name,personsName) + "\n" + getString(R.string.summary_quantity) + quantity + toppingOneMessage + toppingTwoMessage + getString(R.string.total) +
                        itemPrice + getString(R.string.thanks_message);
        return localizedOrderSummaryMessage;
    }

    /*
    * Calculates the price and returns as an int
     */
    private int calculatePrice(boolean toppingOneCheck, boolean toppingTwoCheck) {
        int priceTotal;
        int initialPrice = 5;
        int whipPrice = 1;
        int chocoPrice = 2;
        int whipAndChocoPrice = 3;
        // boolean whipCheck = firstToppingCheck();
        // boolean chocoCheck = secondToppingCheck();
            if( (toppingOneCheck) && (toppingTwoCheck) ) {
                priceTotal = (initialPrice + whipAndChocoPrice);
            }
            else if(toppingOneCheck) {
                priceTotal = (initialPrice + whipPrice);
            }
            else if(toppingTwoCheck) {
                priceTotal = (initialPrice + chocoPrice);
            }
            else {
                priceTotal = initialPrice;
            }
        return priceTotal * quantity;
    }

    /*
    Checks if the boxes for each topping are checked and a boolean value is returned
     */
    private boolean firstToppingCheck(){
        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whipped_cream_button);
        return whippedCreamCheck.isChecked();
    }
    private boolean secondToppingCheck() {
        CheckBox chocolateCheck = (CheckBox) findViewById(R.id.chocolate_button);
        return chocolateCheck.isChecked();
    }

    /*
    * Converts the boolean from the previous two methods into a string true = 'yes' false = 'no'
    * used for the order summary string
     */
    private String toppingCheckConversion(boolean check){
        if (check) {
            return getString(R.string.yes);
        }
        else {
            return getString(R.string.no);
        }
    }

    /*
    * gets input for the name field and converts to string, and returns the string.
    */
    private String name_space() {
        EditText name_string = (EditText) findViewById(R.id.name_string);
        return name_string.getText().toString();
    }

    /*
    * sets the current quantity into the quantity text field used by the increment and decrement buttons
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}