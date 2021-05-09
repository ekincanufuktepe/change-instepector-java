class Unicode {
        public static void main(String[] args) {
                System.out.println("A = \uuu0041");
        }
        private void printSomething() {
        	int a = 6;
        	double x = 2.1;
        	System.out.println(a+x);
        }
        
        public static final int sum(int a, int b) {
        	return a + b;
        }
        
        private Currency div(Currency a, Currency b, Currency c) {
        	c = a / b;
        	return c;
        }
        
        public void methodWithoutFinal() {
        	System.out.println("For AFM rule");
        }
        
        private final void methodWithFinal(String msg) {
        	System.out.println("For DFM rule " + msg);
        }
        
        public static void methodWithStatic() {
        	System.out.println("For DSM rule");
        }
}
