package stackstock;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;



@Data
@Getter
@Setter
@ToString
@Document(value="kospi")
public class Kospi implements Serializable {
    @Id
    private Long id;
    private String stockNumber;
    private String stockPrice;
    private Long tradeVolume;
    private Date getDate = new Date();

}
