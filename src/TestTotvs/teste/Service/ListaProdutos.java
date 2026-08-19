package Service;

import Model.Categoria;
import Model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListaProdutos {

    @Autowired
    List<Produto> produtos ;

    public List<Produto> ListarProdutos(){

        Categoria cat_prod1 = new Categoria();
        cat_prod1.setId(1L);
        cat_prod1.setNome_categoria("alimentos");

        Produto produto1 = new Produto();
        produto1.setNome("melao");
        produto1.setPreco(new BigDecimal("10.0"));
        produto1.setQuantidadeEstoque(5);
        produto1.setCategoria(cat_prod1);
        produtos.add(produto1);

        //Produto2

        Categoria cat_prod2 = new Categoria();
        cat_prod2.setId(2L);
        cat_prod2.setNome_categoria("bebidas");

        Produto produto2 = new Produto();

        produto2.setNome("coca");
        produto2.setPreco(new BigDecimal("5.0"));
        produto2.setQuantidadeEstoque(10);
        produto2.setCategoria(cat_prod2);
        produtos.add(produto2);

        List<Produto> produtosPedidos = new ArrayList<>();

        for (Produto produto : produtos){
            if (produto.getQuantidadeEstoque() < 10){
                produtosPedidos.add(produto);
            }
        }
       return produtosPedidos;
    }



}
