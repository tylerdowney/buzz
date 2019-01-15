public class mutateTesterMain2 {
	private mutateTester mt1;
	public mutateTesterMain2(mutateTester mt){
		this.mt1 = mt;
	}
	public void doIt(){
		mt1.setVar1(100);
		mt1.setVar2(120);
		System.out.println(mt1.getVar1());
		System.out.println(mt1.getVar2());
	}
}
