public class TriangleFace {
	public short vertexIndex1;
	public short vertexIndex2;
	public short vertexIndex3;
	public short colorIndex;

    public TriangleFace(short vertexIndex1, short vertexIndex2, short vertexIndex3, short colorIndex) {
        this.vertexIndex1 = vertexIndex1;
        this.vertexIndex2 = vertexIndex2;
        this.vertexIndex3 = vertexIndex3;
        this.colorIndex = colorIndex;  
    }
}
