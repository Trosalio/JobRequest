package models.forms;

import java.time.LocalDate;

/**
 * Project Name: MemoView
 */
public class MockedForm extends GenericForm {

    private static int staticInt = 0;

    public MockedForm(String recipient){
        dateIssued.set(LocalDate.now());
        subject.set(":DDD");
        this.recipient.set(recipient);
        typeOfForm.set(String.valueOf(staticInt));
        changeStaticInt();
    }


    public void editForm(){
        changeStaticInt();
        setTypeOfForm(String.valueOf(staticInt));
    }

    private void changeStaticInt(){
        staticInt++;
        if(staticInt > 3) staticInt = 0;
    }
}
