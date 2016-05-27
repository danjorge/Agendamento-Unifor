package br.unifor.pin.agendamento.business;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import br.unifor.pin.agendamento.dao.UsuarioDAO;
import br.unifor.pin.agendamento.entity.Anexos;

@Service
@Component
@Controller("anexosBO")
public class AnexosBO {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Context
	HttpServletRequest request;

	private OutputStream stream;
	
	public void handlerFileUpload(Anexos anexo, HttpServletRequest request){
		
		// Converting a Base64 String into Image byte array
        byte[] imageByteArray = decodeImage(anexo.getPhoto());
		
        System.out.println(anexo.getPhoto());
		//Instancia de anexos e preenchimentos de atributos
        anexo.setDatAnexo(new Date());
        anexo.setFlgAtivo(true);
        anexo.setTxtDiretorio("/ssa_files/");
        anexo.setTxtNomeArquivo("foto_usuario_matricula_" +anexo.getUsuarios().getMatricula() + ".png");
		

        
		String path = request.getRealPath("");
		String destinoArquivo = path + "/resources/img/perfil/";
		transfereArquivoParaDestino(imageByteArray, destinoArquivo, anexo.getUsuarios().getMatricula()+ ".png");
		
		if(imageByteArray.length != -1){
			usuarioDAO.salvarAnexo(anexo);
		}
	}
	
	private void transfereArquivoParaDestino(byte[] imageByteArray, String destinoArquivo, String fileName){
		try {
			stream = new FileOutputStream(new File(destinoArquivo + "/" + fileName));
			stream.write(imageByteArray);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
	
}
