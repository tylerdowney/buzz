public class mutateTesterMain {
	public static void main(String[] args){
		mutateTester mt = new mutateTester();
		mt.setVar1(50);
		mt.setVar2(60);
		System.out.println(mt.getVar1());
		System.out.println(mt.getVar2());

		mutateTesterMain2 mt2 = new mutateTesterMain2(mt);
		mt2.doIt();
		System.out.println(mt.getVar1());
		System.out.println(mt.getVar2());
		
	}
}
