FROM alpine:latest
LABEL maintainer="e.hamza.mekaoui@gmail.com"
RUN apk add --update --no-cache openssh
RUN echo 'PasswordAuthentication yes' >> /etc/ssh/sshd_config
RUN adduser -h /home/user -s /bin/sh -D user
RUN echo -n 'user:justapassword' | chpasswd
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 22
COPY entrypoint.sh /
