package rubik2;

import java.util.BitSet;

public class CubeNode {
	public BitSet[] state;
	public String methodName;
	
	public CubeNode(BitSet[] state, String methodName) {
		this.state = new BitSet[state.length];
		for (int i = 0; i < state.length; i++) {
			this.state[i] = state[i].get(0, state[i].size());
		}
		this.methodName = methodName;
	}

}
