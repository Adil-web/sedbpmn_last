package kz.kazgisa.data.dto;

public class FileContentDto extends FileDto {
    private byte[] file;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
