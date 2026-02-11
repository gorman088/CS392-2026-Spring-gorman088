public class TestPayroll {
    public static void main(String[] args) {
        Payroll p = new Payroll();

        // Test 1: constructor / size
        if (p.size() != 0) {
            System.out.println("FAIL: new payroll size should be 0");
            return;
        }
        System.out.println("PASS: constructor sets size to 0");
        p.print();
        System.out.println();

        // Test 2: add_employee
        p.add_employee(new Employee("Alice", 1, 50000.0));
        p.add_employee(new Employee("Bob", 2, 60000.0));

        if (p.size() != 2) {
            System.out.println("FAIL: size should be 2 after adding 2 employees");
            return;
        }
        System.out.println("PASS: add_employee updates size");
        p.print();
        System.out.println();

        // Test 3: find_employee success
        try {
            int idx = p.find_employee("Alice");
            if (idx < 0 || idx >= p.size()) {
                System.out.println("FAIL: returned invalid index for Alice");
                return;
            }
            System.out.println("PASS: found Alice at index " + idx);
        } catch (EmployeeNotFoundException e) {
            System.out.println("FAIL: should not throw for Alice");
            return;
        }
        p.print();
        System.out.println();

        // Test 4: find_employee fail
        try {
            p.find_employee("NotARealName");
            System.out.println("FAIL: should throw when name not found");
            return;
        } catch (EmployeeNotFoundException e) {
            System.out.println("PASS: throws when not found");
        }
        p.print();
        System.out.println();

        // Test 5: remove_employee bad index
        try {
            p.remove_employee(-1);
            System.out.println("FAIL: should throw for -1");
            return;
        } catch (EmployeeIndexException e) {
            System.out.println("PASS: throws for -1");
        }
        p.print();
        System.out.println();

        // Test 6: remove_employee and swap with last
        int oldSize = p.size();
        try {
            p.remove_employee(0);
        } catch (EmployeeIndexException e) {
            System.out.println("FAIL: should not throw");
            return;
        }

        if (p.size() != oldSize - 1) {
            System.out.println("FAIL: size should decrease by 1 after removal");
            return;
        }
        System.out.println("PASS: decreases size");
        p.print();
        System.out.println();

        // Test 7: add_payroll
        Payroll p2 = new Payroll();
        p2.add_employee(new Employee("Carol", 3, 70000.0));
        p2.add_employee(new Employee("Dave", 4, 80000.0));

        System.out.println("Source payroll (p2):");
        p2.print();
        System.out.println();

        int before = p.size();
        p.add_payroll(p2);

        if (p.size() != before + 2) {
            System.out.println("FAIL: should increase size by source size");
            return;
        }
        System.out.println("PASS: increases size");
        p.print();
        System.out.println();

        // Test 8: copy_payroll
        Payroll p3 = new Payroll();
        p3.add_employee(new Employee("X", 5, 90000.0));
        p3.add_employee(new Employee("Y", 6, 100000.0));

        System.out.println("Source payroll (p3):");
        p3.print();
        System.out.println();

        p.copy_payroll(p3);

        if (p.size() != p3.size()) {
            System.out.println("FAIL: should make sizes equal");
            return;
        }

        try {
            p.find_employee("X");
            p.find_employee("Y");
        } catch (EmployeeNotFoundException e) {
            System.out.println("FAIL: should copy employees");
            return;
        }
        System.out.println("PASS: copies employees");
        p.print();
        System.out.println();

        System.out.println("ALL TESTS PASSED");
    }
}
