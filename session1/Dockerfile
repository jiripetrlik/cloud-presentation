FROM fedora

RUN dnf install -y httpd && dnf clean all
RUN echo "Hello from container" > /var/www/html/index.html

CMD /usr/sbin/apachectl -DFOREGROUND

