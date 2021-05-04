package de.fhkiel.advancedjava.dto.path;

public class InvalidStopNameDto extends PathDto {

    public String wrongParameter;
    public String value;
    public String hint;

    public InvalidStopNameDto(String wrongParameter, String value, String hint) {
        this.wrongParameter = wrongParameter;
        this.value = value;
        this.hint = hint;
    }
}
