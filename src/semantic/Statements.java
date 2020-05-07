package semantic;

import lexer.Pack;

import java.util.List;

public interface Statements {
    /**
     * 如果productList中的第一条产生式不属于自己管辖的范围，直接返回，不要进行修改
     *
     * 每次读取产生式和pack都从index=0开始
     * 从packList中每读取一条产生式都要调用remove(0)删除该产生式
     * packList中存放终结符，读取产生式需要的终结符之后也要调用remove(0)把读取过的pack全部删除掉
     * 注意packList productList将在三个人的方法中共用，保证只使用自己需要的产生式和pack
     * @param packList 终结符List
     * @param productList 所有产生式
     * @param instructs 应当生成的三地址指令，直接在List最后进行添加
     * @param quadruple 应当产生的四元式，保证和instructs长度一致
     */
    public void analysis(List<Pack> packList, List<String> productList, List<String> instructs, List<String> quadruple);
}
