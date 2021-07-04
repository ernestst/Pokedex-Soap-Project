package pl.edu.pb.wi.ws.pokedex;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.xml.ws.WebServiceException;

@WebService(endpointInterface = "pl.edu.pb.wi.ws.pokedex.ImageService")
public class ImageServiceImpl implements ImageService {
    @Override
    public String uploadImage(Image data, String code) {
        URL resources = this.getClass().getResource("/");
        if (data != null && resources != null ) {
            File image = new File(resources.getPath() + code + ".png");
            try {
                ImageIO.write((RenderedImage) data, "png", image);
            } catch (IOException e) {
                throw new WebServiceException("Upload Failed!");
            }

            return "success";
        }

        throw new WebServiceException("Upload Failed!");
    }

    @Override
    public Image downloadImage(String code) {
        try {
            URL res = getClass().getClassLoader().getResource(code + ".png");

            if (res != null) {
                File image = Paths.get(res.toURI()).toFile();
                return ImageIO.read(image);
            }
        } catch(URISyntaxException | IOException e) {
            return null;
        }

        return null;
    }
}
